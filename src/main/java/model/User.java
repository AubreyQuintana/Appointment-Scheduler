package model;

/**
 * This class creates the framework for the User objects.
 * @author Aubrey Quintana
 */
public class User {

    private int userID;
    private String userName;
    private String password;

    /**
     * The User class constructor.
     * @param userID The user ID
     * @param userName The username
     * @param password The user's password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     *
     * @return user ID
     */
    public int getUserID() {

        return userID;
    }

    /**
     *
     * @param userID The user ID to set
     */
    public void setUserID(int userID) {

        this.userID = userID;
    }

    /**
     *
     * @return username
     */
    public String getUserName() {

        return userName;
    }

    /**
     *
     * @param userName The username to set
     */
    public void setUserName(String userName) {

        this.userName = userName;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {

        return password;
    }

    /**
     *
     * @param password The password to set
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * This method converts to a string value.
     * @return username
     */
    @Override
    public String toString() {
        return userName;
    }
}
