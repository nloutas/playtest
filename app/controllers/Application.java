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
    // refresh();
    return ok(contactPage.render("Contact Form", contactForm));

  }

  /**
   * @return redirect back to the index page
   */
  public static Result processContact() {
    Contact contact = contactForm.bindFromRequest().get();
    String postAction = request().body().asFormUrlEncoded().get("action")[0];

    if (contact == null) {
      return badRequest(contactPage.render("Unable to " + postAction + ". Please check the logs",
          contactForm));
    }
    switch (postAction) {
      case "save":
        contact.save();
        break;
      case "update":
        contact.update();
        break;
      case "delete":
        contact.delete();
        break;
      default:
        return badRequest(contactPage.render("Invalid action " + postAction
            + ". Only save, update and delete are allowed.", contactForm));
    }

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
