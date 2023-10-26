package com.example.c195;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.DatabaseConnection;

import java.io.IOException;

/**
 * This class runs the start of the software.
 * @author Aubrey Quintana
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 250);
        stage.setTitle("Aubrey Quintana PA");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method. First method that gets called when starting the software.
     * @param args Not used
     */
    public static void main(String[] args) {

        DatabaseConnection.openConnection();
        launch();

        DatabaseConnection.closeConnection();


    }

}