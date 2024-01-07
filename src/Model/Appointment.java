package Model;

import java.time.LocalDateTime;

/**This is the Appointment class.
 * This class represents an appointment in the application.*/
public class Appointment {

    /** The appointment ID. */
    private int id;

    /** The title of the appointment. */
    private String title;

    /** A description of the appointment. */
    private String description;

    /** The location of the appointment. */
    private String location;

    /** The type of appointment. */
    private String type;

    /** The date and time the appointment starts. */
    private LocalDateTime start;

    /** The date and time the appointment ends. */
    private LocalDateTime end;

    /** The ID of the customer associated with the appointment. */
    private int customerID;

    /** The ID of the user who created the appointment. */
    private int userID;

    /** The ID of the contact associated with the appointment. */
    private int contactID;

    /**Constructor to create a new appointment.
     * @param id The appointment ID
     * @param title The title of the appointment
     * @param description A description of the appointment
     * @param location The location of the appointment
     * @param type The type of appointment
     * @param start The date and time the appointment starts
     * @param end The date and time the appointment ends
     * @param customerID The ID of the customer associated with the appointment
     * @param contactID The id of the contact associated with the appointment
     * @param userID The ID of the user who created the appointment*/
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID,  int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**This is the default constructor for the appointment class.*/
    public Appointment(){

    }

    //Getter and setter methods for each appointment field.

    /**Gets the ID of the appointment.
     @return The appointment ID. */
    public int getId() {
        return id;
    }

    /**Sets the ID of the appointment.
     *@param id The appointment ID. */
    public void setId(int id) {
        this.id = id;
    }

    /**Gets the title of the appointment.
     *@return The title of the appointment. */
    public String getTitle() {
        return title;
    }

    /**Sets the title of the appointment.
     *@param title The title of the appointment. */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Gets the description of the appointment.
     *@return A description of the appointment. */
    public String getDescription() {
        return description;
    }

    /**Sets the description of the appointment.
     *@param description A description to set. */
    public void setDescription(String description) {
        this.description = description;
    }

    /**Gets the location of the appointment.
     *@return The location of the appointment. */
    public String getLocation() {
        return location;
    }

    /**Sets the location of the appointment.
     *@param location The location to set. */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Gets the contact associated with the appointment.
     *@return The ID of the contact associated with the appointment. */
    public int getContact() {
        return contactID;
    }

    /**Sets the ID of the contact associated with the appointment.
     *@param contact The ID of the contact to set. */
    public void setContact(int contact) {
        this.contactID = contact;
    }

    /**Gets the type of the appointment.
     *@return The type of the appointment. */
    public String getType() {
        return type;
    }

    /**Sets the type of the appointment.
     *@param type The type to set. */
    public void setType(String type) {
        this.type = type;
    }

    /**Gets the date and time the appointment starts.
     *@return The date and time the appointment starts. */
    public LocalDateTime getStart() {
        return start;
    }

    /**Sets the date and time the appointment starts.
     *@param start The date and time to set. */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**Gets the date and time the appointment ends.
     *@return The date and time the appointment ends. */
    public LocalDateTime getEnd() {
        return end;
    }

    /**Sets the date and time the appointment ends.
     *@param end The date and time to set. */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**Gets the ID of the customer associated with the appointment.
     *@return The ID of the customer associated with the appointment. */
    public int getCustomerID() {
        return customerID;
    }

    /**Sets the ID of the customer associated with the appointment.
     *@param customerID The ID of the customer to set. */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**Gets the ID of the user who created the appointment.
     *@return The ID of the user who created the appointment. */
    public int getUserID() {
        return userID;
    }

    /**Sets the ID of the user who created the appointment.
     *@param userID The ID of the user to set. */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
