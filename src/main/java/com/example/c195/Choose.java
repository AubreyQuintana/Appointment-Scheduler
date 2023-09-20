package com.example.c195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Choose {


    public Button informationBtn;
    public Button appointmentBtn;


    public void OnActionInformation(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
