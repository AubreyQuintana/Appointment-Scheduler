package com.example.c195;

import objectCRUDs.CountryCRUD;
import objectCRUDs.CustomerCRUD;
import objectCRUDs.FirstLevelDivisionCRUD;
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

/**
 * This class is for the Update Customer page.
 * @author Aubrey Quintana
 */
public class UpdateCustomer implements Initializable {
    @FXML
    public Button updateCustomer;
    @FXML
    public Button cancelUpdateCustomer;
    @FXML
    public TextField upCustomerIDTxt;
    @FXML
    public TextField upCustomerNameTxt;
    @FXML
    public TextField upCustomerAddressTxt;
    @FXML
    public TextField upCustomerPhoneTxt;
    @FXML
    public TextField upCustomerZipTxt;
    @FXML
    public ComboBox<FirstLevelDivision> upStateComboBox;
    @FXML
    public ComboBox<Country> upCountryComboBox;
    public static int selectedCustomerIndex;


    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, populating the Country combo box.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate the Country combo box
        try {
            upCountryComboBox.setItems(CountryCRUD.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method allows the user to update any customer information and save to the database.
     * @param event Event occurs when user clicks on the Update button.
     * @throws IOException Throws exception when error loading the Customer page.
     */
    @FXML
    public void OnActionUpdateCustomer(ActionEvent event) throws IOException {

        try {
            //Get data from user input to update in Customer database
            int customerID = Integer.parseInt(upCustomerIDTxt.getText());
            String customerName = upCustomerNameTxt.getText();
            String address = upCustomerAddressTxt.getText();
            String phoneNumber = upCustomerPhoneTxt.getText();
            String zipCode = upCustomerZipTxt.getText();
            Country customerCountry = upCountryComboBox.getValue();
            FirstLevelDivision division = upStateComboBox.getValue();
            int divisionID = division.getDivisionID();

            CustomerCRUD.update(customerID, customerName, address, zipCode, phoneNumber, divisionID);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to navigate back to the Customer page.
     * @param event Event occurs when user clicks on the Cancel button.
     * @throws IOException Throws exception when error loading the Customer page.
     */
    @FXML
    public void OnActionCancelUpdateCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method is used to populate the Update screen with the selected customer's information.
     * @param selectedCustomer User selects a customer from the table on the Customer page.
     * @throws SQLException Throws exception when error connecting to the MySQL database.
     */
    @FXML
    protected void sendCustomerInfo(Customer selectedCustomer) throws SQLException {

        selectedCustomerIndex = CustomerCRUD.getAllCustomers().indexOf(selectedCustomer);

        upCustomerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
        upCustomerNameTxt.setText(selectedCustomer.getCustomerName());
        upCustomerAddressTxt.setText(selectedCustomer.getAddress());
        upCustomerZipTxt.setText(selectedCustomer.getPostalCode());
        upCustomerPhoneTxt.setText(selectedCustomer.getPhoneNumber());

        FirstLevelDivision division = FirstLevelDivisionCRUD.getDivision(selectedCustomer.getDivisionID());
        Country country = CountryCRUD.getCountryByDivision(division.getDivisionID());
        upCountryComboBox.setValue(country);
        upStateComboBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(country.getCountryID()));
        upStateComboBox.setValue(division);
    }

    /**
     * This method is used to properly populate the State combo box based off the selection of the Country combo box.
     * @param mouseEvent Event occurs when user clicks on the State combo box.
     */
    @FXML
    public void OnActionUpStateCBox(MouseEvent mouseEvent) throws SQLException {

        Country countryID = upCountryComboBox.getValue();
        int country = countryID.getCountryID();

        upStateComboBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(country));

    }
}