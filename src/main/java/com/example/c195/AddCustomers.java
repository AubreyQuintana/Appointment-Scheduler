package com.example.c195;

import DAOimplementation.CustomerCRUD;
import DAOinterfaces.CountryDAO;
import DAOinterfaces.CustomerDAO;
import DAOinterfaces.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomers implements Initializable {
    @FXML
    public Button addCustomer;
    @FXML
    public Button cancelAddCustomer;
    @FXML
    public TextField customerNameTxt;
    @FXML
    public TextField customerAddressTxt;
    @FXML
    public TextField customerZipTxt;
    @FXML
    public TextField customerPhoneTxt;
    @FXML
    public ComboBox<String> customerCountryCBox;
    @FXML
    public ComboBox<String> customerStateCBox;

    public static int customerCounter = CustomerDAO.getAllCustomers().size();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        //Lamda expressions - making country name list from countries
        CountryDAO.getAllCountries().forEach(country -> {
            countryNames.add(country.getCountry());
        });

        FirstLevelDivisionDAO.getAllDivisions().forEach(firstLevelDivision -> {
            divisionNames.add(firstLevelDivision.getDivision());
        });

        //if (FirstLevelDivision.countryID)
        customerCountryCBox.setItems(countryNames);
        customerStateCBox.setItems(divisionNames);

    }

    @FXML
    public void OnActionAddCustomer(ActionEvent event) throws IOException {

        try {
            int customerID = customerCounter++;
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String customerZip = customerZipTxt.getText();
            String customerPhone = customerPhoneTxt.getText();
            int customerDivisionID = 0;  //- not accurate, how to give division ID when given country and state
            String customerCountry = customerCountryCBox.getValue();
            String customerState = customerStateCBox.getValue();

            //this gives country ID, not division ID -- NEED TO FIX FOR DIVISION ID
            switch (customerCountry) {
                case "U.S" -> customerDivisionID = 1;
                case "UK" -> customerDivisionID = 2;
                case "Canada" -> customerDivisionID = 3;
            }

            int rowsAffected = CustomerCRUD.insert(customerName, customerAddress, customerZip, customerPhone, customerDivisionID);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Records.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each field");
            alert.showAndWait();
        }
    }

    @FXML
    public void OnActionCancelAddCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Records.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //public void CustomerCountryCBox{

        /*ObservableList<String> countryComboBox = FXCollections.observableArrayList(
                "United States",
                "United Kingdom",
                "Canada"
        );

        customerCountryCBox.setItems(countryComboBox);
    }

    @FXML
    public void OnActionCustomerStateCBox(ActionEvent event) {

        ObservableList<String> stateComboBox = FXCollections.observableArrayList(
                "Alabama", "Alaska", "Arizona"
        );

        customerStateCBox.setItems(stateComboBox);
    } */

}


