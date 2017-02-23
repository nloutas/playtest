package controllers;

import static play.libs.Json.toJson;

import akka.actor.ActorInitializationException;
import akka.actor.ActorKilledException;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;


import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.*;

import scala.concurrent.duration.Duration;
import scala.reflect.ClassTag;

import com.avaje.ebean.Model;

import models.Contact;
import play.Logger;
import play.api.cache.CacheApi;
import play.cache.Cached;
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

  private final static Duration duration = Duration.create("30 seconds");
  private final ClassTag<List<Contact>> classTag =
      scala.reflect.ClassTag$.MODULE$.apply(List.class);

  @Inject
  CacheApi cacheApi;



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
  @Cached(key = "ContactsJson")
  public Result getContactsJson() {
    // TODO: use CacheApi getOrElse method
    // cacheApi.getOrElse("contacts", duration, () -> getDBContacts() , classTag);
    return ok(toJson(getDBContacts()));
  }

  private List<Contact> getDBContacts() {
    //use cache if available
    if (cacheApi.get("contacts", classTag).nonEmpty()) {
      List<Contact> contacts = cacheApi.get("contacts", classTag).get();
      contacts.forEach(contact -> {
        Logger.info("* Cached contact = " + contact.toString());
      });
      return contacts;
    }


    //get fresh from DB
    List<Contact> contacts = new Model.Finder<Long, Contact>(Long.class, Contact.class).all();


    cacheApi.set("contacts", contacts, duration);
    Logger.debug("*** caching contacts = " + cacheApi.get("contacts", classTag));

    return contacts;
  }
}
