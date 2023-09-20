package com.example.c195;

import DAOimplementation.*;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAOimplementation.AppointmentsCRUD.getAllAppointments;

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

    public void initialize(URL url, ResourceBundle rb) {

        try {
            apptContactCBox.setItems(ContactCRUD.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            apptCustomerCBox.setItems(CustomerCRUD.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            apptUserCBox.setItems(UsersCRUD.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 0);

        //NEED TO ADD CONDITIONS FOR OVERLAPPING APPOINTMENTS
        //DONT FORGET TO EXCLUDE CURRENT APPT TIME USING APPT ID
        while (start.isBefore(end.plusSeconds(1))) {
            startTimeCBox.getItems().add(start);
            endTimeCBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    public void OnActionAddAppointment(ActionEvent event) throws IOException {

        try {
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
            int contactID = contact.getContactID();
            Customer customer = apptCustomerCBox.getValue();
            int customerID = customer.getCustomerID();
            User user = apptUserCBox.getValue();
            int userID = user.getUserID();

            //Convert LocalTime & LocalDate to one object
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            ZoneId zoneID = ZoneId.systemDefault();
            ZonedDateTime startZDT = ZonedDateTime.of(startDateTime, zoneID);
            ZonedDateTime endDZT = ZonedDateTime.of(endDateTime, zoneID);


            //Appointment class wants LocalDateTime object, not ZonedDateTime object soooo???
            String startDT = startZDT.toLocalDate().toString() + " " + startZDT.toLocalTime().toString();

            //ERROR if start date is after end date
            if (startDate.isAfter(endDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Your start date must be before your end date.");
                alert.showAndWait();
                return;
            }

            //ERROR if start time is after end time
            if (startTime.isAfter(endTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Your start time must be before your end time.");
                alert.showAndWait();
                return;
            }

            //ERROR if entering an incorrect contact, customer, user -- not sure if I need since I changed to comboboxes??
            if (ContactCRUD.getAllContacts().contains(contact) ||
                    CustomerCRUD.getAllCustomers().contains(customer) ||
                    UsersCRUD.getAllUsers().contains(user)) {

                AppointmentsCRUD.insert(title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Please choose a correct contact ID, customer ID");
                alert.showAndWait();
                return;
            }

            //Once information is entered, takes you back to appointment table
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each field");
            alert.showAndWait();
        }

    }

    public void OnActionCancelAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
