package com.example.c195;

import DAOimplementation.CountryCRUD;
import DAOimplementation.CustomerCRUD;
import DAOimplementation.FirstLevelDivisionCRUD;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

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
    public ComboBox<Country> customerCountryCBox;
    @FXML
    public ComboBox<FirstLevelDivision> customerStateCBox;

    public static int customerCounter = CustomerCRUD.counter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            customerCountryCBox.setItems(CountryCRUD.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerStateCBox.setItems(FirstLevelDivisionCRUD.getAllDivisions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void OnActionStateCBox(MouseEvent event) throws SQLException {

        Country country = customerCountryCBox.getValue(); //NULL VALUE BECAUSE VALUE ISN'T ACTUALLY STORED ANYWHERE
        int countryID = country.getCountryID();

        customerStateCBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(countryID));

    }
    @FXML
    public void OnActionAddCustomer(ActionEvent event) throws IOException {

        try {
            int customerID = customerCounter++;
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String customerZip = customerZipTxt.getText();
            String customerPhone = customerPhoneTxt.getText();
            Country customerCountry = customerCountryCBox.getValue();
            FirstLevelDivision customerState = customerStateCBox.getValue();

            int customerDivisionID = customerState.getDivisionID();

            int rowsAffected = CustomerCRUD.insert(customerName, customerAddress, customerZip, customerPhone, customerDivisionID);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
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
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}


