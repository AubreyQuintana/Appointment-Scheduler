package com.example.c195;

import DAOimplementation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 250);
        stage.setTitle("Aubrey Quintana PA");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        DatabaseConnection.openConnection();
        //int rowsAffected = QueryExecution.updateCustomer(3, "Dudley Do-Right");
        CustomerCRUD.select();
        CountryCRUD.select();
        FirstLevelDivisionCRUD.select();
        AppointmentsCRUD.select();
        ContactCRUD.select();

        launch();
        DatabaseConnection.closeConnection();


    }

}