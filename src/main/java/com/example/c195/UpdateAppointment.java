package com.example.c195;

import DAOimplementation.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

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
    public TextField upApptStartTimeTxt;
    @FXML
    public TextField upApptEndTimeTxt;
    @FXML
    public ComboBox<Customer> upApptCustomerCBox;
    @FXML
    public ComboBox<User> upApptUserCBox;
    public static int selectedAppointmentIndex;
    public ComboBox<LocalTime> upStartTimeCBox;
    public ComboBox<LocalTime> upEndTimeCBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            upApptContactCBox.setItems(ContactCRUD.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            upApptCustomerCBox.setItems(CustomerCRUD.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            upApptUserCBox.setItems(UsersCRUD.getAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 0);

        //NEED TO ADD CONDITIONS FOR OVERLAPPING APPOINTMENTS
        while (start.isBefore(end.plusSeconds(1))) {
            upStartTimeCBox.getItems().add(start);
            upEndTimeCBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    public void OnActionUpdateAppointment(ActionEvent event) throws IOException {

        try {
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

            //Convert local date & local time to one LDT object
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            //Convert Object values into integers to update in appointment class
            int contactID = contact.getContactID();
            int customerID = customer.getCustomerID();
            int userID = user.getUserID();

            AppointmentsCRUD.update(apptID, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);

        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void OnActionCancelUpdateAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();


    }

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
