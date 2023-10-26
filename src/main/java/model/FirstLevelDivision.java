package model;

/**
 * This class creates the framework for the First Level Division objects.
 * @author Aubrey Quintana
 */
public class FirstLevelDivision {

    private int divisionID;
    private String division;
    private int countryID;

    /**
     * The First Level Division class constructor
     * @param divisionID The division ID
     * @param division The division name
     * @param countryID The country ID associated with the division
     */
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     *
     * @return division ID
     */
    public int getDivisionID() {

        return divisionID;
    }

    /**
     *
     * @param divisionID The division ID to set
     */
    public void setDivisionID(int divisionID) {

        this.divisionID = divisionID;
    }

    /**
     *
     * @return division name
     */
    public String getDivision() {

        return division;
    }

    /**
     *
     * @param division The division name to set
     */
    public void setDivision(String division) {

        this.division = division;
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
     * @param countryID The country ID to set
     */
    public void setCountryID(int countryID) {

        this.countryID = countryID;
    }

    /**
     * This method converts to a string value
     * @return division name
     */
    @Override
    public String toString() {
        return division;
    }
}
