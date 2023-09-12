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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateRecord implements Initializable {
    public Button updateCustomer;
    public Button cancelUpdateCustomer;
    public TextField upCustomerNameTxt;
    public TextField upCustomerAddressTxt;
    public TextField upCustomerPhoneTxt;
    public TextField upCustomerZipTxt;
    public static int selectedCustomerIndex;
    public ComboBox<String> upStateComboBox;
    public ComboBox<String> upCountryComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();

        //Lamda expressions - making country name list from countries
        CountryDAO.getAllCountries().forEach(country -> {
            countryNames.add(country.getCountry());
        });

        //Lamda expression - making state name list from first level division
        FirstLevelDivisionDAO.getAllDivisions().forEach(firstLevelDivision -> {
            divisionNames.add(firstLevelDivision.getDivision());
        });

        //if (selectedCustomerIndex.ge == "U.S")
        upCountryComboBox.setItems(countryNames);
        upStateComboBox.setItems(divisionNames);

    }

    public void OnActionUpdateCustomer(ActionEvent event) throws IOException {

        try {
            String customerName = upCustomerNameTxt.getText();
            String address = upCustomerAddressTxt.getText();
            String phoneNumber = upCustomerPhoneTxt.getText();
            String zipCode = upCustomerZipTxt.getText();

            CustomerCRUD.update(selectedCustomerIndex, customerName, address, phoneNumber, zipCode);

        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Records.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void OnActionCancelUpdateCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Records.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    protected void sendCustomerInfo(Customer selectedCustomer) {
        selectedCustomerIndex = CustomerDAO.getAllCustomers().indexOf(selectedCustomer);

        //customerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
        upCustomerNameTxt.setText(selectedCustomer.getCustomerName());
        upCustomerAddressTxt.setText(selectedCustomer.getAddress());
        upCustomerZipTxt.setText(selectedCustomer.getPostalCode());
        upCustomerPhoneTxt.setText(selectedCustomer.getPhoneNumber());
        upCountryComboBox.getValue();
        upStateComboBox.getValue();

        //NEED TO ADD COMBO BOX ITEMS

    }
}
