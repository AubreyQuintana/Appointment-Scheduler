package com.example.c195;

import DAOimplementation.CountryCRUD;
import DAOimplementation.CustomerCRUD;
import DAOimplementation.FirstLevelDivisionCRUD;
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

public class UpdateCustomer implements Initializable {
    public Button updateCustomer;
    public Button cancelUpdateCustomer;
    public TextField upCustomerIDTxt;
    public TextField upCustomerNameTxt;
    public TextField upCustomerAddressTxt;
    public TextField upCustomerPhoneTxt;
    public TextField upCustomerZipTxt;
    public static int selectedCustomerIndex;
    public ComboBox<FirstLevelDivision> upStateComboBox;
    public ComboBox<Country> upCountryComboBox;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            upCountryComboBox.setItems(CountryCRUD.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnActionUpdateCustomer(ActionEvent event) throws IOException {

        try {
            int customerID = Integer.parseInt(upCustomerIDTxt.getText());
            String customerName = upCustomerNameTxt.getText();
            String address = upCustomerAddressTxt.getText();
            String phoneNumber = upCustomerPhoneTxt.getText();
            String zipCode = upCustomerZipTxt.getText();
            Country customerCountry = upCountryComboBox.getValue();
            FirstLevelDivision division = upStateComboBox.getValue();
            int divisionID = division.getDivisionID();

            String divisionName = division.getDivision();
            String countryName = customerCountry.getCountry();
            //String fullAddress = address + ", " + divisionName + ", " + countryName;

            CustomerCRUD.update(customerID, customerName, address, zipCode, phoneNumber, divisionID);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionCancelUpdateCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    protected void sendCustomerInfo(Customer selectedCustomer) throws SQLException {

        selectedCustomerIndex = CustomerCRUD.getAllCustomers().indexOf(selectedCustomer);

        upCustomerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
        upCustomerNameTxt.setText(selectedCustomer.getCustomerName());
        upCustomerAddressTxt.setText(selectedCustomer.getAddress());
        upCustomerZipTxt.setText(selectedCustomer.getPostalCode());
        upCustomerPhoneTxt.setText(selectedCustomer.getPhoneNumber());

        try {
            FirstLevelDivision division = FirstLevelDivisionCRUD.getDivision(selectedCustomer.getDivisionID());
            Country country = CountryCRUD.getCountryByDivision(division.getDivisionID());
            upCountryComboBox.setValue(country);
            if (upCountryComboBox.getValue() != null) {
                upStateComboBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(country.getCountryID()));
                upStateComboBox.setValue(division);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void OnActionUpStateCBox(MouseEvent mouseEvent) {
        if (upCountryComboBox != null) {
            Country countryID = upCountryComboBox.getValue();
            int country = countryID.getCountryID();

            try {
                upStateComboBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(country));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}