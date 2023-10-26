package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;

/**
 * This class holds methods related to the MySQL Database.
 * @author Aubrey Quintana
 */
public class AppointmentsCRUD {

    /**
     * This method allows insertion of Appointments into the database.
     * @param title The title
     * @param description The description
     * @param location The location
     * @param type The type
     * @param startDateTime The start date & time
     * @param endDateTime The end date & time
     * @param customerID The customer ID
     * @param userID The user ID
     * @param contactID The contact ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static void insert(String title, String description, String location, String type,
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
    }

    /**
     * This method allows the update to data in the Appointment table of the MySQL database.
     * @param appointmentID The appointment ID
     * @param title The title
     * @param description The description
     * @param location The location
     * @param type The type
     * @param startDateTime The start date & time
     * @param endDateTime The end date & time
     * @param customerID The customer ID
     * @param userID The user ID
     * @param contactID The contact ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
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

    /**
     * This method allows the deletion of a specific appointment from the MySQL Database.
     * @param appointmentID The appointment ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static void delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM CLIENT_SCHEDULE.APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
    }

    /**
     * This method allows the user to get all appointments from a specific customer.
     * @param customerID The customer ID
     * @return List of all appointments from a specific customer
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Appointment> getCustomerAppointments(int customerID) throws SQLException {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CLIENT_SCHEDULE.APPOINTMENTS WHERE Customer_ID = ? and Start >= now();";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("APPOINTMENT_ID");
            LocalDateTime startDateTime = rs.getTimestamp("START").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("END").toLocalDateTime();
            int customerIDFK = rs.getInt("CUSTOMER_ID");
            Appointment appointment = new Appointment(appointmentID, startDateTime, endDateTime, customerIDFK);
            customerAppointments.add(appointment);
        }
        return customerAppointments;
    }

    /**
     * This method is used to check the overlapping of scheduled appointments.
     * @param customerAppointments List of appointments by a specific customer
     * @param start Start time of appointment
     * @param end End time of appointment
     * @param excludeApptID Appointment ID to be excluded
     * @return True if an overlap does occur, False if an overlap does not occur.
     */
    public static boolean checkOverlap(ObservableList<Appointment> customerAppointments, LocalDateTime start, LocalDateTime end, int excludeApptID){
        for(Appointment a : customerAppointments) {
            if (a.getAppointmentID() == excludeApptID) {
                continue;
            }
            if (a.getStartDateTime().equals(start)) {
                return true;
            }
            else if (a.getEndDateTime().equals(end)) {
                return true;
            }
            else if (start.isAfter(a.getStartDateTime()) && start.isBefore(a.getEndDateTime())) {
                return true;
            }
            else if (end.isAfter(a.getStartDateTime()) && end.isBefore(a.getEndDateTime())) {
                return true;
            }
            else if (a.getStartDateTime().isAfter(start) && a.getStartDateTime().isBefore(end)) {
                return true;
            }
            else if (a.getEndDateTime().isAfter(start) && a.getEndDateTime().isBefore(end)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method allows user to retrieve all appointments from the MySQL Database.
     * @return List of all appointments
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        if (allAppointments.isEmpty()) {
            String sql = "SELECT * FROM CLIENT_SCHEDULE.APPOINTMENTS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
                allAppointments.add(appointment);
            }
        }
        return allAppointments;
    }

    //Lamda expression filtering appointments by Customer to ensure Customers who have appointments cant be deleted

    /**
     * This method uses a Lambda expression to filter appointments by a specific customer.
     * @param customerID customer ID
     * @return List of all appointments by a specific customer.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Appointment> getAppointmentsByCustomer(int customerID) throws SQLException {
        ObservableList<Appointment> list = getAllAppointments().stream()
                .filter(apt -> apt.getCustomerID() == customerID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

    /**
     * This method uses a Lambda expression to filter appointments by a specific contact.
     * @param contactID contact ID
     * @return List of all appointments by a specific contact.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Appointment> getAppointmentsByContact(int contactID) throws SQLException {
        ObservableList<Appointment> list = AppointmentsCRUD.getAllAppointments().stream()
                .filter(apt -> apt.getContactID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

    //Get type of appointment from database to populate the type combo box in reports screen

    /**
     * This method retrieves all Types of appointments from MySQL Database, excluding redundancies.
     * @return List of all unique appointments types.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<String> getTypeOfAppointment() throws SQLException {
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        if (appointmentTypes.isEmpty()) {
            String sql = "SELECT DISTINCT TYPE FROM CLIENT_SCHEDULE.APPOINTMENTS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("TYPE");
            appointmentTypes.add(type);
            }
        }
        return appointmentTypes;
    }

    //Get month of appointment from database to populate the month combo box in reports screen

    /**
     * This method retrieves all the month's that appointments take place.
     * @return List of all month's that contact appointments.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Month> getMonthOfAppointment() throws SQLException {
        ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
        if (appointmentMonths.isEmpty()) {
            String sql = "SELECT DISTINCT MONTH(START) AS THEMONTH FROM CLIENT_SCHEDULE.APPOINTMENTS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Month month = Month.of(rs.getInt("THEMONTH"));
                appointmentMonths.add(month);
            }
        }
        return appointmentMonths;
    }

    //find out how many appointments match a certain type and month parameter

    /**
     * This method uses a Lambda expression that filters appointments by Type and Month.
     * @param type Type
     * @param month Month
     * @return List of all appointments that match specific type and month.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Appointment> getAppointmentsByTypeAndMonth(String type, Month month) throws SQLException {
        ObservableList<Appointment> list = AppointmentsCRUD.getAllAppointments().stream()
                .filter(apt -> apt.getType().equals(type) && apt.getStartDateTime().getMonth().equals(month))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }
}
