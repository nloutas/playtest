package controllers;

import static play.libs.Json.toJson;

import java.util.List;

import models.Contact;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

/**
 * @author nikos
 *
 */
public class Application extends Controller {
  /**
   * @return home page
   */
  public static Result index() {
    return ok(index.render("Play Home"));
  }

  /**
   * @return Contact form for new contact
   */
  public static Result addContact() {
    final Form<Contact> contactForm = Form.form(Contact.class);
    return ok(contactPage.render("Contact Form", contactForm));
  }

  /**
   * @return Contact form for given id
   */
  public static Result editContact(Long contact_id) {
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
  public static Result processContact() {
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
        new Model.Finder<>(Long.class, Contact.class).byId(contact.getContact_id()).delete();
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
  public static Result getContacts() {
    List<Contact> contacts = new Model.Finder(Long.class, Contact.class).all();
    return ok(contactList.render(contacts));
  }

  /**
   * @return list of contacts in json format
   */
  public static Result getContactsJson() {
    List<Contact> contacts = new Model.Finder(Long.class, Contact.class).all();
    return ok(toJson(contacts));
  }
}
