package com.example.c195;

import DAOimplementation.AppointmentsCRUD;
import DAOinterfaces.AppointmentsDAO;
import DAOinterfaces.ContactDAO;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

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
    public TextField apptCustomerIDTxt;
    @FXML
    public TextField apptUserIDTxt;
    @FXML
    public TextField apptStartTimeTxt;
    @FXML
    public TextField apptEndTimeTxt;
    @FXML
    public ComboBox<String> apptLocation;
    @FXML
    public ComboBox<String> apptContactCBox;
    @FXML
    public DatePicker apptStartDate;
    @FXML
    public DatePicker apptEndDate;

    public static int apptCounter = AppointmentsDAO.getAllAppointments().size();

    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> contactNames = FXCollections.observableArrayList();

        ContactDAO.getAllContacts().forEach(contact -> {
            contactNames.add(contact.getContactName());
        });

        apptContactCBox.setItems(contactNames);

        //just experimenting with adding string to combo box. what are the actual locations??
        ObservableList<String> locationComboBox = FXCollections.observableArrayList(
                "United States",
                "United Kingdom",
                "Canada"
        );

        apptLocation.setItems(locationComboBox);
    }

    public void OnActionAddAppointment(ActionEvent event) throws IOException {

        try {
            int apptID = apptCounter++;
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocation.getValue();
            String type = apptTypeTxt.getText();
            String startTime = apptStartTimeTxt.getText();
            String endTime = apptEndTimeTxt.getText();
            LocalDate startDate = apptStartDate.getValue();
            LocalDate endDate = apptEndDate.getValue();
            int customerID = Integer.parseInt((apptCustomerIDTxt.getText()));
            int userID = Integer.parseInt(apptUserIDTxt.getText());
            String contact = apptContactCBox.getValue();

            LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTime));
            LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.parse(endTime));

            int contactID = switch (contact) {
                case "Anika Costa" -> 1;
                case "Daniel Garcia" -> 2;
                case "Li Lee" -> 3;
                default -> 0;
            };

            int rowsAffected = AppointmentsCRUD.insert(title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID);

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

    public void OnActionApptLocationCBox(ActionEvent event) {
    }

    public void OnActionApptContactCBox(ActionEvent event) {
    }
}
