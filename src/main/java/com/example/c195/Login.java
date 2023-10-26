package com.example.c195;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import objectCRUDs.AppointmentsCRUD;
import objectCRUDs.UsersCRUD;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the user's first point of contact. Prompts the user to login with username and password to verify identity.
 * @author Aubrey Quintana
 */
public class Login implements Initializable {
    @FXML
    public TextField userNameTxt;
    @FXML
    public TextField passwordTxt;
    @FXML
    public Label locationTxt;
    @FXML
    public Button loginBtn;
    @FXML
    public Button Exit;
    @FXML
    public Text welcomeLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label timeZoneLabel;
    ResourceBundle rb2 = ResourceBundle.getBundle("com.example/c195/Translate", Locale.getDefault());

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as checking for the System's zoneID and translating to the default language settings.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Check system's zoneID and change Location text to that Zone's name
        ZoneId location = ZoneId.systemDefault();
        locationTxt.setText(String.valueOf(location));

        //Translate text to french if System default language settings are in French
        loginBtn.setText(rb2.getString("login"));
        welcomeLabel.setText(rb2.getString("welcomemessage"));
        timeZoneLabel.setText(rb2.getString("location"));
        usernameLabel.setText(rb2.getString("username"));
        passwordLabel.setText(rb2.getString("password"));
        Exit.setText(rb2.getString("exit"));

    }

    /**
     * Verifies correct login information, records every login attempt, and checks for upcoming appointments for the user.
     * @param event Event raised by clicking the login button.
     * @throws IOException Throws when problems loading the Choose FXML file.
     * @throws SQLException Throws when problems connecting to the MySQL database.
     */
    @FXML
    public void OnActionLoginBtn(ActionEvent event) throws IOException, SQLException {

        String userInput = userNameTxt.getText();
        String passwordInput = passwordTxt.getText();

        boolean isValid = UsersCRUD.doesUserExist(userInput, passwordInput);
        boolean hasAppointment = false;
        int appointmentID = 0;
        LocalTime startTime = null;
        LocalDate startDate = null;
        ObservableList<Appointment> userAppointments = AppointmentsCRUD.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();

        //Checks login info against database to make sure credentials are valid
        if (isValid) {

            //Record successful login attempts to txt file in Root directory
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(
                        new File("login_activity.txt"),
                        true /* append = true */));
                //Note that Notepad will not display the following lines on separate lines
                pw.append(userInput + " " + now + " " + "- Successful\n");
                pw.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (Appointment a : userAppointments) {
                //If user has upcoming appointment, boolean changes to true and breaks out of the loop
                if (a.getStartDateTime().isAfter(now) && a.getStartDateTime().isBefore(now.plusMinutes(15))) {
                    hasAppointment = true;
                    appointmentID = a.getAppointmentID();
                    startTime = a.getStartDateTime().toLocalTime();
                    startDate = a.getStartDateTime().toLocalDate();
                    break;
                }
            }
            //If user has an upcoming appointment, alert is sent
            if (hasAppointment) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("You have Appointment ID: " + appointmentID + " on "
                        + startDate + " at " + startTime + " which is within the next 15 minutes.");
                alert.showAndWait();

            }
            //If user does not have an upcoming appointment, alert is sent
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("You have no upcoming appointments.");
                alert.showAndWait();
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            //Record unsuccessful login attempts to txt file in Root directory
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(
                        new File("login_activity.txt"),
                        true /* append = true */));
                //Note that Notepad will not display the following lines on separate lines
                pw.append(userInput + " " + now + " " + "- Unsuccessful\n");
                pw.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Access denied");
            alert.setContentText(rb2.getString("alertmessage"));
            alert.showAndWait();
        }
    }

    /**
     * User can exit the program.
     * @param event Event occurs when user clicks the exit button.
     */
    @FXML
    public void OnActionExitBtn(ActionEvent event) {

        System.exit(0);

    }
}
