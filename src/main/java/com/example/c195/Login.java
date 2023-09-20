package com.example.c195;

import DAOimplementation.AppointmentsCRUD;
import DAOimplementation.UsersCRUD;
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
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Login {

    public TextField userNameTxt;
    public TextField passwordTxt;
    public Label locationTxt;
    public Button loginBtn;
    public Button Exit;
    Stage stage;
    Parent scene;

    //lamda expression to loop all appointments check all start times, then alert if do have appt or do not
    public void OnActionLoginBtn(ActionEvent event) throws IOException, SQLException {

        String userInput = userNameTxt.getText();
        String passwordInput = passwordTxt.getText();

        //User userName = UsersCRUD.getUserByName(userInput);
        //User passWord = UsersCRUD.getUserByPassWord(passwordInput);

        if (userInput.isEmpty() || passwordInput.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter a username or password.");
            alert.showAndWait();
        }
        else {
              if (UsersCRUD.doesUserExist(userInput, passwordInput)) {
                  LocalDateTime now = LocalDateTime.now();

                  //loop all appointments check all start times, then alert if do have appt or do not
                  ObservableList<Appointment> allAppointments = AppointmentsCRUD.getAllAppointments();
                  allAppointments.forEach(a-> {
                              if (a.getStartDateTime().isAfter(now) && a.getStartDateTime().isBefore(now.plusMinutes(15))) {
                                  //dont want to alert for each appointment in loop though
                                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                  alert.setTitle("Upcoming Appointment");
                                  alert.setContentText("You have Appointment ID: " + a.getAppointmentID() + " on "
                                          + a.getStartDateTime().toLocalDate() + " at " + a.getStartDateTime().toLocalTime()
                                          + " which is within the next 15 minutes.");
                                  alert.showAndWait();
                              } else {
                                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                  alert.setTitle("Upcoming Appointment");
                                  alert.setContentText("You have no upcoming appointments.");
                                  alert.showAndWait();
                              }
                          });

                  Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                  Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
                  stage.setScene(new Scene(scene));
                  stage.show();

              }
              else {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error dialogue");
                  alert.setContentText("Username or password entered is incorrect. Please try again.");
                  alert.showAndWait();
              }

        }

    }

    public void OnActionExitBtn(ActionEvent event) {

        System.exit(0);

    }
}
