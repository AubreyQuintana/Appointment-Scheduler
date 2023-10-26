package com.example.c195;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is for the Update Appointment page.
 * @author Aubrey Quintana
 */
public class UpdateAppointment implements Initializable {
    @FXML
    public Button upAddAppointment;
    @FXML
    public Button cancelUpdateAppointment;
    @FXML
    public TextField upApptIDtxt;
    @FXML
    public TextField upApptTitleTxt;
    @FXML
    public TextField upApptTypeTxt;
    @FXML
    public TextField upApptLocationTxt;
    @FXML
    public ComboBox<Contact> upApptContactCBox;
    @FXML
    public TextArea upApptDescTxt;
    @FXML
    public DatePicker upApptStartDate;
    @FXML
    public DatePicker upApptEndDate;
    @FXML
    public ComboBox<Customer> upApptCustomerCBox;
    @FXML
    public ComboBox<User> upApptUserCBox;
    @FXML
    public ComboBox<LocalTime> upStartTimeCBox;
    @FXML
    public ComboBox<LocalTime> upEndTimeCBox;
    public static int selectedAppointmentIndex;

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, populating all combo boxes
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate the Contact combo box with all contacts
        try {
            upApptContactCBox.setItems(ContactCRUD.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate the Customer combo box with all customers
        try {
            upApptCustomerCBox.setItems(CustomerCRUD.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate the User combo box with all users
        try {
            upApptUserCBox.setItems(UsersCRUD.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Populate time combo boxes with local timezone times based off EST business hours
        upStartTimeCBox.setItems(TimeUtility.getStartTimes());
        upEndTimeCBox.setItems(TimeUtility.getEndTimes());
    }

    /**
     * This method allows the user to update any appointment information and save to the database.
     * @param event Event occurs when user clicks on Update button.
     * @throws IOException Throws exception when error loading the Update Appointment page.
     */
    @FXML
    public void OnActionUpdateAppointment(ActionEvent event) throws IOException {

        try {
            //Get data from user input to update in Appointment database
            int apptID = Integer.parseInt(upApptIDtxt.getText());
            String title = upApptTitleTxt.getText();
            String description = upApptDescTxt.getText();
            String location = upApptLocationTxt.getText();
            String type = upApptTypeTxt.getText();
            LocalTime startTime = upStartTimeCBox.getValue();
            LocalTime endTime = upEndTimeCBox.getValue();
            LocalDate startDate = upApptStartDate.getValue();
            LocalDate endDate = upApptEndDate.getValue();
            Customer customer = upApptCustomerCBox.getValue();
            User user = upApptUserCBox.getValue();
            Contact contact = upApptContactCBox.getValue();
            //Convert combo box values into integers to update in Appointment class
            int contactID = contact.getContactID();
            int customerID = customer.getCustomerID();
            int userID = user.getUserID();
            //Convert local date and local time to one LDT object to update in Appointment class
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
            boolean isOverlapping = AppointmentsCRUD.checkOverlap(customerAppointments, startDateTime, endDateTime, apptID);
            if (isOverlapping) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("You already have an appointment scheduled at that time.");
                alert.showAndWait();
            }
            else {
                AppointmentsCRUD.update(apptID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            }

            //If pass all checks, new Appointment is added and takes you back to Appointment screen
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
     * @param event Event occurs when user clicks on Cancel button.
     * @throws IOException Throws exception when error loading Appointment page.
     */
    @FXML
    public void OnActionCancelUpdateAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method is used to populate the Update screen with the selected appointment's information.
     * @param selectedAppointment User selects an appointment from the table on the Appointment page.
     * @throws SQLException Throws exception when error connecting to the MySQL database.
     */
    @FXML
    protected void sendAppointmentInfo(Appointment selectedAppointment) throws SQLException {

        selectedAppointmentIndex = AppointmentsCRUD.getAllAppointments().indexOf(selectedAppointment);

        upApptIDtxt.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        upApptTitleTxt.setText(selectedAppointment.getTitle());
        upApptDescTxt.setText(selectedAppointment.getDescription());
        upApptLocationTxt.setText(selectedAppointment.getLocation());
        upApptTypeTxt.setText(selectedAppointment.getType());
        upApptStartDate.setValue(selectedAppointment.getStartDateTime().toLocalDate());
        upStartTimeCBox.setValue(selectedAppointment.getStartDateTime().toLocalTime());
        upApptEndDate.setValue(selectedAppointment.getEndDateTime().toLocalDate());
        upEndTimeCBox.setValue(selectedAppointment.getEndDateTime().toLocalTime());
        upApptContactCBox.setValue(ContactCRUD.getContact(selectedAppointment.getContactID()));
        upApptCustomerCBox.setValue(CustomerCRUD.getCustomer(selectedAppointment.getCustomerID()));
        upApptUserCBox.setValue(UsersCRUD.getUserByID(selectedAppointment.getUserID()));
    }

}
