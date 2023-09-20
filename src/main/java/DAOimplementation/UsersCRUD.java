package DAOimplementation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersCRUD {
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();

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

    public static boolean doesUserExist(String userName, String password) throws SQLException {
        String sql = "SELECT count(*) FROM client_schedule.users WHERE User_Name = ? AND password = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

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
