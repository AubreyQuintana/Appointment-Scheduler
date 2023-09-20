package com.example.c195;

import DAOimplementation.AppointmentsCRUD;
import DAOimplementation.CustomerCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public TableColumn<Appointment, LocalDateTime> apptStartDateTimeCol;
    public TableColumn<Appointment, LocalDateTime> apptEndDateTimeCol;
    public TableColumn<Appointment, Integer> apptCustomerIDCol;
    public TableColumn<Appointment, Integer> apptUserIDCol;
    public TableColumn<Appointment, Integer> apptContactIDCol;
    public ToggleGroup appointmentTG;


    public void initialize(URL url, ResourceBundle rb) {

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

        try {
            apptTableView.setItems(AppointmentsCRUD.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void OnActionUpdateAppointment(ActionEvent event) throws IOException, SQLException {

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

    public void OnActionDeleteAppt(ActionEvent event) throws SQLException {

        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select an appointment from the list to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm deletion");
            alert.setContentText("Are you sure you want to cancel this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                AppointmentsCRUD.delete(selectedAppointment.getAppointmentID());
                apptTableView.getItems().remove(selectedAppointment);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Cancelled");
                alert1.setContentText("The " + selectedAppointment.getType() + " appointment of Appointment ID: " + selectedAppointment.getAppointmentID() + " was cancelled.");
                alert1.showAndWait();

            }
        }

        }

    public void OnActionViewByMonthRBtn(ActionEvent event) throws SQLException {
        //AppointmentsCRUD.getAllAppointments().sort(apptTableView.getItems());
        int currentMonths = LocalDateTime.now().getMonth().getValue();
        ObservableList<Appointment> curMonthAppt = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentsCRUD.getAllAppointments();
        allAppointments.forEach(a -> {
            if (a.getStartDateTime().getMonth().getValue() == currentMonths) {
                curMonthAppt.add(a);
            }
        });
        apptTableView.setItems(curMonthAppt);
    }

    public void OnActionViewByWeekRBtn(ActionEvent event) throws SQLException {
        int currentWeeks = LocalDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        ObservableList<Appointment> curWeekAppt = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentsCRUD.getAllAppointments();
        allAppointments.forEach(a -> {
            if (a.getStartDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == currentWeeks) {
                curWeekAppt.add(a);
            }
        });
        apptTableView.setItems(curWeekAppt);
    }

    public void OnActionAllApptRBtn(ActionEvent event) throws SQLException {
        apptTableView.setItems(AppointmentsCRUD.getAllAppointments());
    }
}
