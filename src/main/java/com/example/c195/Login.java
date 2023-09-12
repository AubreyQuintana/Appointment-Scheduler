package com.example.c195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {

    Stage stage;
    Parent scene;

    public void OnActionLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionExit(ActionEvent event) {

        System.exit(0);

    }
}
