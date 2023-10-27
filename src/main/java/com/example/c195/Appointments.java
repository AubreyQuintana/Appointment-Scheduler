package com.example.c195;

import javafx.fxml.FXML;
import objectCRUDs.AppointmentsCRUD;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class represents your Appointments page.
 * Holds all appointment data and displays appointment information.
 * @author Aubrey Quintana
 */
public class Appointments implements Initializable {
    @FXML
    public Button backBtn;
    @FXML
    public Button addAppointmentBtn;
    @FXML
    public Button updateAppointmentBtn;
    @FXML
    public TableView<Appointment> apptTableView;
    @FXML
    public TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    public TableColumn<Appointment, String> apptTitleCol;
    @FXML
    public TableColumn<Appointment, String> apptDescCol;
    @FXML
    public TableColumn<Appointment, String> apptLocationCol;
    @FXML
    public TableColumn<Appointment, String> apptTypeCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptStartDateTimeCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptEndDateTimeCol;
    @FXML
    public TableColumn<Appointment, Integer> apptCustomerIDCol;
    @FXML
    public TableColumn<Appointment, Integer> apptUserIDCol;
    @FXML
    public TableColumn<Appointment, Integer> apptContactIDCol;
    public ToggleGroup appointmentTG;

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, populating the Appointment tableview.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate Appointments table
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
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allows the user to navigate back to the Choose page.
     * @param event Event occurs when user clicks on the Back button.
     * @throws IOException Throws exception if error loading Choose page.
     */
    @FXML
    public void OnActionBackBtn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows to the user to navigate to the Add Appointment page.
     * @param event Event occurs when user clicks on the Add button.
     * @throws IOException Throws exception if error loading Add Appointment page.
     */
    @FXML
    public void OnActionAddAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to navigate to the Update Appointment page.
     * @param event Event occurs when user clicks on Update button.
     * @throws IOException Throws exception if error loading the Update Appointment page.
     * @throws SQLException Throws exception if error connecting to the MySQL database.
     */
    @FXML
    public void OnActionUpdateAppointment(ActionEvent event) throws IOException, SQLException {

        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateAppointment.fxml"));

        //Checks to see if user has selected an appointment from the table
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select an appointment from the list to update");
            alert.showAndWait();
        }
        else {
            //Once selected, that appointment information is sent to the Update Appointment screen
            Parent parent = loader.load();
            UpdateAppointment addAppointment = loader.getController();
            addAppointment.sendAppointmentInfo(selectedAppointment);
            Scene scene = new Scene(parent, 400, 600);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method allows a user to delete an appointment from the database.
     * @param event Event occurs when user clicks on the Delete button.
     * @throws SQLException Throws exception if error connecting to the MySQL database.
     */
    @FXML
    public void OnActionDeleteAppt(ActionEvent event) throws SQLException {

        Appointment selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        //Checks to see if user has selected an appointment from the table
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

    /**
     * This method is used to filter the table view by appointments in the current month.
     * Using a lambda expression to sort through each appointment and add appointments in the current month to a separate list.
     * @param event Event occurs when the View by Month radio button is selected.
     * @throws SQLException Throws exception if error connecting to the MySQL database.
     */
    @FXML
    public void OnActionViewByMonthRBtn(ActionEvent event) throws SQLException {

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

    /**
     * This method is used to filter the table view by appointments in the current week.
     * Using a lambda expression to sort through each appointment and add appointments in the current week to a separate list.
     * @param event Event occurs when the View by Week radio button is selected.
     * @throws SQLException Throws exception if error connecting to the MySQL database.
     */
    @FXML
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

    /**
     * This method is used to view all appointments in the table view.
     * @param event Event occurs when the View All radio button is selected.
     * @throws SQLException Throws exception if error connecting to the MySQL database.
     */
    @FXML
    public void OnActionAllApptRBtn(ActionEvent event) throws SQLException {

        apptTableView.setItems(AppointmentsCRUD.getAllAppointments());
    }

    /**
     * This method is used to navigate to the Reports page.
     * @param event Event occurs when user clicks on the View Reports button.
     * @throws IOException Throws exception if error loading the Reports page.
     */
    @FXML
    public void OnActionViewReportsBtn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}