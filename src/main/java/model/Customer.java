package model;

/**
 * This class creates the framework for the Customer objects.
 * @author Aubrey Quintana
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String divisionName;
    private String countryName;

    /**
     * The Customer class constructor
     * @param customerID The customer ID
     * @param customerName The customer name
     * @param address The customer's address
     * @param postalCode The customer's zipcode
     * @param phoneNumber The customer's phone number
     * @param divisionID The customer's division ID
     * @param divisionName The customer's division name
     * @param countryName The customer's country name
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /**
     *
     * @return customer ID
     */
    public int getCustomerID() {

        return customerID;
    }

    /**
     *
     * @param customerID the customer ID to set
     */
    public void setCustomerID(int customerID) {

        this.customerID = customerID;
    }

    /**
     *
     * @return customer name
     */
    public String getCustomerName() {

        return customerName;
    }

    /**
     *
     * @param customerName The customer name to set
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     *
     * @return customer address
     */
    public String getAddress() {

        return address;
    }

    /**
     *
     * @param address The customer address to set
     */
    public void setAddress(String address) {

        this.address = address;
    }

    /**
     *
     * @return customer zip code
     */
    public String getPostalCode() {

        return postalCode;
    }

    /**
     *
     * @param postalCode The customer zip code to set
     */
    public void setPostalCode(String postalCode) {

        this.postalCode = postalCode;
    }

    /**
     *
     * @return customer phone number
     */
    public String getPhoneNumber() {

        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber The customer phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return customer's division ID
     */
    public int getDivisionID() {

        return divisionID;
    }

    /**
     *
     * @param divisionID The customer's division ID to set
     */
    public void setDivisionID(int divisionID) {

        this.divisionID = divisionID;
    }

    /**
     *
     * @return customer's division name
     */
    public String getDivisionName() {

        return divisionName;
    }

    /**
     *
     * @param divisionName The customer's divison name to set
     */
    public void setDivisionName(String divisionName) {

        this.divisionName = divisionName;
    }

    /**
     *
     * @return customer's country name
     */
    public String getCountryName() {

        return countryName;
    }

    /**
     *
     * @param countryName The customer's country name to set
     */
    public void setCountryName(String countryName) {

        this.countryName = countryName;
    }

    /**
     * This method converts to a string value
     * @return customer name
     */
    @Override
    public String toString() {
        return customerName;
    }



}
