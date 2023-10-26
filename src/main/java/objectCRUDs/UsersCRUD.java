package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utility.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class holds methods related to the MySQL Database.
 * @author Aubrey Quintana
 */
public class UsersCRUD {
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * This method retrieves all information from a specific user.
     * @param userID user ID
     * @return User object containing all parameters for User class.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static User getUserByID(int userID) throws SQLException {
            String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userIDFK = rs.getInt("USER_ID");
                String userName = rs.getString("USER_NAME");
                String password = rs.getString("PASSWORD");
                User userResult = new User(userIDFK, userName, password);
                return userResult;
            }
        return null;
    }

    /**
     * This method uses a boolean to determine if a User's username and password match those in the MySQL Database.
     * @param userName username
     * @param password password
     * @return True if parameters match, False if parameters do not match.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static boolean doesUserExist(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = ? AND password = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    /**
     * This method retrieves all User information from the MySQL Database.
     * @return List of all users
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        if (allUsers.isEmpty()) {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("USER_ID");
                String userName = rs.getString("USER_NAME");
                String password = rs.getString("PASSWORD");
                User user = new User(userID, userName, password);
                allUsers.add(user);
            }
        }
        return allUsers;
    }

}
