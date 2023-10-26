package com.example.c195;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class allows the user to navigate through the program.
 * @author Aubrey Quintana
 */
public class Choose {
    @FXML
    public Button informationBtn;
    @FXML
    public Button appointmentBtn;
    @FXML
    public Button viewReportsBtn;

    /**
     * This method allows the user to navigate to the Customers page.
     * @param event Event occurs when user clicks the Customer button.
     * @throws IOException Throws exception when error loading the Customer page.
     */
    @FXML
    public void OnActionInformation(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to navigate to the Appointments page.
     * @param event Event occurs when user clicks the Appointments button.
     * @throws IOException Throws exception when error loading the Appointments page.
     */
    @FXML
    public void OnActionAppointment(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Appointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to navigate to the Reports page.
     * @param event Event occurs when user clicks the View Reports button.
     * @throws IOException Throws exception when error loading the Reports page.
     */
    @FXML
    public void OnActionViewReportsBtn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
