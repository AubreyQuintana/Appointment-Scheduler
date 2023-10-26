package model;

import java.time.LocalDateTime;

/**
 * This class creates the framework for the Appointment objects.
 * @author Aubrey Quintana
 */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * The Appointment class constructor
     * @param appointmentID The appointment ID
     * @param title The title of appointment
     * @param description The description of appointment
     * @param location The location of appointment
     * @param type The type of appointment
     * @param startDateTime The start date/time of appointment
     * @param endDateTime The end date/time of appointment
     * @param customerID The customer ID associated with appointment
     * @param userID The user ID associated with appointment
     * @param contactID The contact ID associated with appointment
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * The Appointment constructor used for converting time zones
     * @param appointmentID The appointment ID
     * @param startDateTime The start date/time of appointment
     * @param endDateTime The end date/time of appointment
     * @param customerID The customer ID associated with appointment
     */
    public Appointment(int appointmentID, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) {
        this.appointmentID = appointmentID;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
    }

    /**
     *
     * @return appointment ID
     */
    public int getAppointmentID() {

        return appointmentID;
    }

    /**
     *
     * @param appointmentID the ID to set
     */
    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    /**
     *
     * @return appointment title
     */
    public String getTitle() {

        return title;
    }

    /**
     *
     * @param title the title to set
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     *
     * @return appointment description
     */
    public String getDescription() {

        return description;
    }

    /**
     *
     * @param description the description to set
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     *
     * @return appointment location
     */
    public String getLocation() {

        return location;
    }

    /**
     *
     * @param location the location to set
     */
    public void setLocation(String location) {

        this.location = location;
    }

    /**
     *
     * @return appointment type
     */
    public String getType() {

        return type;
    }

    /**
     *
     * @param type the type to set
     */
    public void setType(String type) {

        this.type = type;
    }

    /**
     *
     * @return appointment start date/time
     */
    public LocalDateTime getStartDateTime() {

        return startDateTime;
    }

    /**
     *
     * @param startDateTime the start date/time to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {

        this.startDateTime = startDateTime;
    }

    /**
     *
     * @return appointment end date/time
     */
    public LocalDateTime getEndDateTime() {

        return endDateTime;
    }

    /**
     *
     * @param endDateTime the end date/time to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {

        this.endDateTime = endDateTime;
    }

    /**
     *
     * @return appointment customerID
     */
    public int getCustomerID() {

        return customerID;
    }

    /**
     *
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID){

        this.customerID = customerID;
    }

    /**
     *
     * @return appointment userID
     */
    public int getUserID() {

        return userID;
    }

    /**
     *
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {

        this.userID = userID;
    }

    /**
     *
     * @return appointment contactID
     */
    public int getContactID() {

        return contactID;
    }

    /**
     *
     * @param contactID the contact ID to set
     */
    public void setContactID(int contactID) {

        this.contactID = contactID;
    }

}
