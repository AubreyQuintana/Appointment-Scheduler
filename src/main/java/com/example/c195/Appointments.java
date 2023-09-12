package com.example.c195;

import DAOinterfaces.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Appointments implements Initializable {
    public Button backBtn;
    public Button addAppointmentBtn;
    public Button updateAppointmentBtn;
    public TableView<Appointment> apptTableView;
    public TableColumn<Appointment, Integer> apptIDCol;
    public TableColumn<Appointment, String> apptTitleCol;
    public TableColumn<Appointment, String> apptDescCol;
    public TableColumn<Appointment, String> apptLocationCol;
    public TableColumn<Appointment, String> apptTypeCol;
    public TableColumn<Appointment, String> apptStartDateTimeCol;
    public TableColumn<Appointment, String> apptEndDateTimeCol;
    public TableColumn<Appointment, Integer> apptCustomerIDCol;
    public TableColumn<Appointment, Integer> apptUserIDCol;
    public TableColumn<Appointment, Integer> apptContactIDCol;


    public void initialize(URL url, ResourceBundle rb){

        apptTableView.setItems(AppointmentsDAO.getAllAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apptContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }



    public void OnActionBackBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionUpdateAppointment(ActionEvent event) throws IOException {

        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateAppointment.fxml"));

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select an appointment from the list to update");
            alert.showAndWait();
        } else {
            Parent parent = loader.load();
            UpdateAppointment addAppointment = loader.getController();
            addAppointment.sendAppointmentInfo(selectedAppointment);
            Scene scene = new Scene(parent, 400, 600);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
