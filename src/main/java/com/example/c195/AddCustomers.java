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
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class allows the user to add new customer data.
 * @author Aubrey Quintana
 */
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
    public int customerCounter = 4;

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, populating the Country and State combo boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate country combo box
        try {
            customerCountryCBox.setItems(CountryCRUD.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Populate state combo box, initially
        try {
            customerStateCBox.setItems(FirstLevelDivisionCRUD.getAllDivisions());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method populates the State combo box based off selection of Country combo box.
     * @param event Event occurs when user clicks on the State combo box.
     * @throws SQLException Throws exception when errors connecting to the MySQL database.
     */
    @FXML
    public void OnActionStateCBox(MouseEvent event) throws SQLException {

        //Populate state combo box based off country combo box selection
        Country country = customerCountryCBox.getValue();
        int countryID = country.getCountryID();

        customerStateCBox.setItems(FirstLevelDivisionCRUD.getDivisionsByCountry(countryID));
    }

    /**
     * This method allows the user to input data into the add customer form.
     * @param event Event occurs when user clicks the Add button.
     * @throws IOException Throws exception when error loading the Customer page.
     */
    @FXML
    public void OnActionAddCustomer(ActionEvent event) throws IOException {

        try {
            //Get data from user input to add to the Customer database
            int customerID = customerCounter++;
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String customerZip = customerZipTxt.getText();
            String customerPhone = customerPhoneTxt.getText();
            Country customerCountry = customerCountryCBox.getValue();
            FirstLevelDivision customerState = customerStateCBox.getValue();
            int customerDivisionID = customerState.getDivisionID();

            CustomerCRUD.insert(customerName, customerAddress, customerZip, customerPhone, customerDivisionID);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each field");
            alert.showAndWait();
        }
    }

    /**
     * This method allows the user to navigate back to the Customer page.
     * @param event Event occurs when user clicks the Cancel button.
     * @throws IOException Throws exception when error loading the Customer page.
     */
    @FXML
    public void OnActionCancelAddCustomer(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}


