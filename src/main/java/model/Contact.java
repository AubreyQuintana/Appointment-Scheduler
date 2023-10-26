package model;

/**
 * This class creates the framework for the Contact objects.
 * @author Aubrey Quintana
 */
public class Contact {

    private int contactID;
    private String contactName;
    private String email;

    /**
     * Contact class constructor
     * @param contactID The contact ID
     * @param contactName The contact name
     * @param email The contact email
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     *
     * @return contact ID
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

    /**
     *
     * @return contact name
     */
    public String getContactName() {

        return contactName;
    }

    /**
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    /**
     *
     * @return contact email
     */
    public String getEmail() {

        return email;
    }

    /**
     *
     * @param email the contact email to set
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * This method converts to a string value
     * @return contact name
     */
    @Override
    public String toString() {
        return contactName;
    }

}
