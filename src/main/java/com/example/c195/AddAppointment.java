package com.example.c195;

import javafx.collections.FXCollections;
import objectCRUDs.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utility.TimeUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class allows the user to add new appointment data.
 * It holds the Appointment data and displays Appointment information.
 * @author Aubrey Quintana
 */
public class AddAppointment implements Initializable {
    @FXML
    public Button addAppointment;
    @FXML
    public Button cancelAddAppointment;
    @FXML
    public TextField apptIDtxt;
    @FXML
    public TextField apptTitleTxt;
    @FXML
    public TextField apptTypeTxt;
    @FXML
    public TextArea apptDescTxt;
    @FXML
    public TextField apptLocationTxt;
    @FXML
    public ComboBox<Contact> apptContactCBox;
    @FXML
    public DatePicker apptStartDate;
    @FXML
    public DatePicker apptEndDate;
    @FXML
    public ComboBox<Customer> apptCustomerCBox;
    @FXML
    public ComboBox<User> apptUserCBox;
    @FXML
    public ComboBox<LocalTime> startTimeCBox;
    @FXML
    public ComboBox<LocalTime> endTimeCBox;
    public static int apptCounter = 4;

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, creating the Appointment table view.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate the Contact combo box with Contact names
        try {
            apptContactCBox.setItems(ContactCRUD.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate the Customer combo box with Customer names
        try {
            apptCustomerCBox.setItems(CustomerCRUD.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate the User combo box with usernames
        try {
            apptUserCBox.setItems(UsersCRUD.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate time combo boxes with local timezone times based off EST business hours
        startTimeCBox.setItems(TimeUtility.getStartTimes());
        endTimeCBox.setItems(TimeUtility.getEndTimes());
    }

    /**
     * This method allows the user to input data into the add appointment form.
     * @param event Event occurs when user clicks on Add button.
     * @throws IOException Throws exception when error loading the Add Appointment page.
     * @throws SQLException Throws exception when error connecting to the MySQL database.
     */
    @FXML
    public void OnActionAddAppointment(ActionEvent event) throws IOException, SQLException {

        try {
            //Get data from user input to add to the Appointment database
            int apptID = apptCounter++;
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocationTxt.getText();
            String type = apptTypeTxt.getText();
            LocalTime startTime = startTimeCBox.getValue();
            LocalTime endTime = endTimeCBox.getValue();
            LocalDate startDate = apptStartDate.getValue();
            LocalDate endDate = apptEndDate.getValue();
            Contact contact = apptContactCBox.getValue();
            Customer customer = apptCustomerCBox.getValue();
            User user = apptUserCBox.getValue();
            //Convert combo box values to the associated IDs to add to Appointment class
            int contactID = contact.getContactID();
            int customerID = customer.getCustomerID();
            int userID = user.getUserID();
            //Convert local date and local time to one LDT object to add to Appointment class
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            //Check to make sure appointment times are in chronological order
            if (startDateTime.isAfter(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Your start time must be before your end time.");
                alert.showAndWait();
                return;
            }

            //Check to make sure appointment times don't overlap with existing appointments of the same customer
            ObservableList<Appointment> customerAppointments = AppointmentsCRUD.getCustomerAppointments(customerID);
            boolean isOverlapping = AppointmentsCRUD.checkOverlap(customerAppointments, startDateTime, endDateTime, -1);
            if (isOverlapping) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("You already have an appointment scheduled at that time.");
                alert.showAndWait();
                return;
            } else {
                //If pass all checks, new Appointment is added to the database
                AppointmentsCRUD.insert(title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each field");
            alert.showAndWait();
        }
    }

    /**
     * This method allows the user to navigate to the Appointment page.
     * @param event Event occurs when user clicks on the Cancel button.
     * @throws IOException Throws exception when error loading the Appointment page.
     */
    @FXML
    public void OnActionCancelAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
