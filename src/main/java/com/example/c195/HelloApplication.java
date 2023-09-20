package com.example.c195;

import DAOimplementation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.DatabaseConnection;
import utility.TimeConversion;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 250);
        stage.setTitle("Aubrey Quintana PA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        DatabaseConnection.openConnection();
        launch();

        /*ResourceBundle rb = ResourceBundle.getBundle("main/java/utility/RB_fr.properties", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("hello"));
        }*/



        DatabaseConnection.closeConnection();


    }

}