package DAOimplementation;

import DAOinterfaces.AppointmentsDAO;
import model.Appointment;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentsCRUD {

    public static int counter = AppointmentsDAO.getAllAppointments().size();

    public static int insert(String title, String description, String location, String type,
                                        LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (TITLE, DESCRIPTION, LOCATION, TYPE, START, END, CUSTOMER_ID, USER_ID, CONTACT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void update(int appointmentID, String title, String description, String location, String type,
                                         LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET TITLE = ?, DESCRIPTION = ?, LOCATION = ?, TYPE = ?, START = ?, END = ?, CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ? WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);
        int rowsAffected = ps.executeUpdate();
    }

    public static void delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS"; //WHERE PARAMETER = ? THEN USE SETTER TO DEFINE BIND VARIABLE, NAME VARIABLE NAMEFK
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("APPOINTMENT_ID");
            String title = rs.getString("TITLE");
            String description = rs.getString("DESCRIPTION");
            String location = rs.getString("LOCATION");
            String type = rs.getString("TYPE");
            LocalDateTime startDateTime = rs.getTimestamp("START").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("END").toLocalDateTime();
            int customerID = rs.getInt("CUSTOMER_ID");
            int userID = rs.getInt("USER_ID");
            int contactID = rs.getInt("CONTACT_ID");
            Appointment appointment = new Appointment (appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            AppointmentsDAO.addAppointment(appointment);
        }
    }

}
