package controllers;

import static play.libs.Json.toJson;

import akka.actor.ActorRef;

import java.util.List;

import javax.inject.*;

import scala.concurrent.duration.Duration;
import scala.reflect.ClassTag;

import com.avaje.ebean.Model;

import models.Contact;
import play.Logger;
import play.api.cache.CacheApi;
import play.cache.Cached;
import play.cache.Cache;
import play.data.Form;
import play.mvc.*;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;
import views.html.*;

/**
 * @author nikos
 *
 */
public class Application extends Controller {

  private final static Duration duration30 = Duration.create("30 seconds");

  @Inject
  CacheApi cacheApi;

  private ClassTag<ActorRef> classTag = scala.reflect.ClassTag$.MODULE$.apply(List.class);

  // @Inject
  // @NamedCache("session-cache")
  // CacheApi sessionCache;

  // @Inject
  // public static JedisPool jedisPool;
  //
  // public static Jedis jedis = null;


  /**
   * @return home page
   */
  @Cached(key = "homePage")
  public Result index() {
    return ok(index.render("Play Home"));
  }

  /**
   * @return Contact form for new contact
   */
  public Result addContact() {
    final Form<Contact> contactForm = Form.form(Contact.class);
    return ok(contactPage.render("Contact Form", contactForm));
  }

  /**
   * @return Contact form for given id
   */
  public Result editContact(Long contact_id) {
    Contact contact = Contact.find.byId( contact_id);
    if (contact == null) {
      final Form<Contact> contactForm = Form.form(Contact.class);
      return badRequest(contactPage.render("Cannot find Contact with Id " + contact_id + ".",
          contactForm));
    }
    final Form<Contact> contactForm = Form.form(Contact.class).fill( contact );
    return ok(contactPage.render("Contact Form", contactForm));
  }

  /**
   * @return redirect to the contacts page
   */
  public Result processContact() {
    String postAction = request().body().asFormUrlEncoded().get("action")[0];
    final Form<Contact> contactForm = Form.form(Contact.class).bindFromRequest();
    if (contactForm.hasErrors()) {
      return badRequest(contactPage.render("Given data has errors", contactForm));
    }
    Contact contact = contactForm.get();
    if (contact == null) {
      return badRequest(contactPage.render("Unable to " + postAction + ". Please check the logs",
          contactForm));
    }

    switch (postAction) {
      case "save":
        if (contact.getContact_id() != null) {
          contact.update();
        } else {
          contact.save();
        }
        break;
      case "delete":
        new Model.Finder<Long, Contact>(Long.class, Contact.class).byId(contact.getContact_id())
            .delete();
        break;
      default:
        return badRequest(contactPage.render("Invalid action " + postAction
            + ". Only save, update and delete are allowed.", contactForm));
    }

    return redirect(routes.Application.getContacts());
  }

  /**
   * @return list of contacts
   */
  public Result getContacts() {
    return ok(contactList.render(getDBContacts()));
  }

  /**
   * @return list of contacts in json format
   */
  public Result getContactsJson() {
    return ok(toJson(getDBContacts()));
  }

  private List<Contact> getDBContacts() {
    //use cache if available

    // if (jedis == null) {
    // jedis = jedisPool.getResource();
    // }
    //
    // if (jedis.exists("contacts")) {
    // Logger.info("*cache from jedis contacts = " + jedis.get("contacts"));
    // }
    // cacheApi.getOrElse("contacts", duration30, arg2, classTag);

    //get fresh from DB
    List<Contact> contacts = new Model.Finder<Long,Contact> (Long.class, Contact.class).all();

    Cache.set("contacts", contacts);
    // jedis.set("contacts", contacts.toString());
    // Logger.info("*new jedis contacts = " + jedis.get("contacts"));

    cacheApi.set("contacts", contacts, duration30);
    Logger.info("* cacheApi contacts = " + cacheApi.get("contacts", classTag));

    return contacts;
  }
}
