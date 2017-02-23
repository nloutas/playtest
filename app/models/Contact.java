/**
 * model for contacts table
 */
package models;

import java.sql.Date;

import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.validation.*;
import play.data.format.*;


/**
 * @author nikos
 *
 */
@Entity
public class Contact extends Model {

  private static final long serialVersionUID = 1L;

  @Id
  private Long contact_id;

  private String contact_type_id;
  private String referral_type_id;


  private String title;

  private String first_name;

  private String last_name;

  private String organization;

  private String address;

  private String city;

  private String postal_code;
  private String state_id;
  private String country;
  // @Constraints.Required
  private String phone;
  @Constraints.Email
  private String email;

  @Formats.DateTime(pattern = "dd/MM/yyyy")
  private Date last_updated = new Date(System.currentTimeMillis());
  private String notes;

  public static Finder<Long, Contact> find = new Finder<Long, Contact>(Long.class, Contact.class);



  /**
   * @return the contact_id
   */
  public Long getContact_id() {
    return contact_id;
  }

  /**
   * @return the contact_type_id
   */
  public String getContact_type_id() {
    return contact_type_id;
  }

  /**
   * @return the referral_type_id
   */
  public String getReferral_type_id() {
    return referral_type_id;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the first_name
   */
  public String getFirst_name() {
    return first_name;
  }

  /**
   * @return the last_name
   */
  public String getLast_name() {
    return last_name;
  }

  /**
   * @return the organization
   */
  public String getOrganization() {
    return organization;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the postal_code
   */
  public String getPostal_code() {
    return postal_code;
  }

  /**
   * @return the state_id
   */
  public String getState_id() {
    return state_id;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return the last_updated
   */
  public Date getLast_updated() {
    return last_updated;
  }

  /**
   * @return the notes
   */
  public String getNotes() {
    return notes;
  }

  /**
   * @param contact_id the contact_id to set
   */
  public void setContact_id(Long contact_id) {
    this.contact_id = contact_id;
  }

  /**
   * @param contact_type_id the contact_type_id to set
   */
  public void setContact_type_id(String contact_type_id) {
    this.contact_type_id = contact_type_id;
  }

  /**
   * @param referral_type_id the referral_type_id to set
   */
  public void setReferral_type_id(String referral_type_id) {
    this.referral_type_id = referral_type_id;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param first_name the first_name to set
   */
  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  /**
   * @param last_name the last_name to set
   */
  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  /**
   * @param organization the organization to set
   */
  public void setOrganization(String organization) {
    this.organization = organization;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @param postal_code the postal_code to set
   */
  public void setPostal_code(String postal_code) {
    this.postal_code = postal_code;
  }

  /**
   * @param state_id the state_id to set
   */
  public void setState_id(String state_id) {
    this.state_id = state_id;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @param last_updated the last_updated to set
   */
  public void setLast_updated(Date last_updated) {
    this.last_updated = last_updated;
  }

  /**
   * @param notes the notes to set
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

}
