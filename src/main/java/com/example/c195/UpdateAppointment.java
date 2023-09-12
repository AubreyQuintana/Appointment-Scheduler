package com.example.c195;

import DAOinterfaces.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.util.Objects;

public class UpdateAppointment {
    @FXML
    public Button upAddAppointment;
    @FXML
    public Button updateAppointment;
    @FXML
    public Button cancelUpdateAppointment;
    @FXML
    public TextField upApptIDtxt;
    @FXML
    public TextField upApptTitleTxt;
    @FXML
    public TextField upApptTypeTxt;
    @FXML
    public ComboBox<String> upApptLocation;
    @FXML
    public ComboBox<String> upApptContactCBox;
    @FXML
    public TextArea upApptDescTxt;
    @FXML
    public TextField upApptCustomerIDTxt;
    @FXML
    public TextField upApptUserIDTxt;
    @FXML
    public DatePicker upApptStartDate;
    @FXML
    public DatePicker upApptEndDate;
    @FXML
    public TextField upApptStartTimeTxt;
    @FXML
    public TextField upApptEndTimeTxt;
    public static int selectedAppointmentIndex;
    public void OnActionUpdateAppointment(ActionEvent event) throws IOException {

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
    protected void sendAppointmentInfo(Appointment selectedAppointment) {
        selectedAppointmentIndex = AppointmentsDAO.getAllAppointments().indexOf(selectedAppointment);

        //customerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
        upApptTitleTxt.setText(selectedAppointment.getTitle());
        upApptTypeTxt.setText(selectedAppointment.getType());
        upApptLocation.getSelectionModel().getSelectedItem(); //HOW TO GET VALUE FROM COMBO BOX
        upApptContactCBox.getSelectionModel().getSelectedItem();
        upApptDescTxt.setText(selectedAppointment.getDescription());
        upApptStartDate.getValue();
        //upApptStartTimeTxt.setText(selectedAppointment.getStartDateTime());
        upApptEndDate.getValue();
        //upApptEndTimeTxt.setText(selectedAppointment.getEndDateTime());
        upApptCustomerIDTxt.setText(String.valueOf(selectedAppointment.getCustomerID()));
        upApptUserIDTxt.setText(String.valueOf(selectedAppointment.getUserID()));

    }
}
