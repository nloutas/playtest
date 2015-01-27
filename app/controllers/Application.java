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
  final static Form<Contact> contactForm = Form.form(Contact.class);
  /**
   * @return home page
   */
  public static Result index() {
    return ok(index.render("Play Home"));
  }

  /**
   * @return Contact page
   */
  public static Result contactForm() {
    return ok(contactPage.render("Add a new contact", contactForm));

  }

  /**
   * @return redirect back to the index page
   */
  public static Result addContact() {
    Contact contact = contactForm.bindFromRequest().get();
    if (contact == null) {
      return badRequest(contactPage.render("Unable to save. Please check the logs", contactForm));
    }
    contact.save();
    return redirect(routes.Application.getContacts());
  }

  /**
   * @return list of contacts in json format
   */
  public static Result getContacts() {
    List<Contact> contacts = new Model.Finder(String.class, Contact.class).all();
    return ok(toJson(contacts));
  }
}
