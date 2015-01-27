/**
 * model for contacts table
 */
package models;

import java.sql.Date;

import javax.persistence.*;

import play.db.ebean.*;
// import play.data.validation.*;
import play.data.format.*;


/**
 * @author nikos
 *
 */
@Entity
public class Contact extends Model {

  private static final long serialVersionUID = -5227973134571381237L;

  // @Id
  // // @Constraints.Min(10)
  // public Long contact_id = 1l;

  public String contact_type_id;
  public String referral_type_id;


  public String title;

  public String first_name;

  public String last_name;

  public String organization;

  public String address;

  public String city;

  public String postal_code;
  public String state_id;
  public String country;

  public String phone;
  // @Constraints.Email
  public String email;

  @Formats.DateTime(pattern = "dd/MM/yyyy")
  public Date last_updated = new Date(System.currentTimeMillis());
  public String notes;

  public static Finder<Long,Contact> find = new Finder<Long,Contact>(
      Long.class, Contact.class
      );

}
