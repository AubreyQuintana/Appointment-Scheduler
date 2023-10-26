package model;

/**
 * This class creates the framework for the Country objects.
 * @author Aubrey Quintana
 */
public class Country {

    private int countryID;
    private String country;

    /**
     * The Country class constructor
     * @param countryID The country ID
     * @param country The country name
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     *
     * @return country ID
     */
    public int getCountryID() {

        return countryID;
    }

    /**
     *
     * @param countryID the country ID to set
     */
    public void setCountryID(int countryID) {

        this.countryID = countryID;
    }

    /**
     *
     * @return the country name
     */
    public String getCountry() {

        return country;
    }

    /**
     *
     * @param country the country name to set
     */
    public void setCountry(String country) {

        this.country = country;
    }

    /**
     * This method converts to a string value
     * @return country name
     */
    @Override
    public String toString() {
        return country;
    }
}
