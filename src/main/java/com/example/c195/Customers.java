package com.example.c195;

import DAOimplementation.AppointmentsCRUD;
import DAOimplementation.CustomerCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class Customers implements Initializable {
    @FXML
    public Button cBackBtn;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Button updateCustomerBtn;
    @FXML
    public TableView<Customer> customerTableView;
    @FXML
    public TableColumn<Customer, Integer> customerIDCol;
    @FXML
    public TableColumn<Customer, String> customerNameCol;
    @FXML
    public TableColumn<Customer, String> customerAddressCol;
    @FXML
    public TableColumn<Customer,String> customerStateCol;
    @FXML
    public TableColumn<Customer, String> customerCountryCol;
    @FXML
    public TableColumn<Customer, String> customerZipCol;
    @FXML
    public TableColumn<Customer, String> customerPhoneCol;



    public void initialize(URL url, ResourceBundle rb) {

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        try {
            customerTableView.setItems(CustomerCRUD.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void OnActionCBackBtn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void OnActionAddRecord(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void OnActionUpdateRecord(ActionEvent event) throws IOException, SQLException {

        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateCustomer.fxml"));

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a customer from the list to update.");
            alert.showAndWait();
        } else {
            Parent parent = loader.load();
            UpdateCustomer addCustomer = loader.getController();
            addCustomer.sendCustomerInfo(selectedCustomer);
            Scene scene = new Scene(parent, 400, 500);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    public void OnActionDeleteCustomerBtn(ActionEvent event) throws SQLException {

        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a Customer from the list to delete.");
            alert.showAndWait();
        } else {
            if (AppointmentsCRUD.getAppointmentsByCustomer(selectedCustomer.getCustomerID()).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm deletion");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    CustomerCRUD.delete(selectedCustomer.getCustomerID());
                    customerTableView.getItems().remove(selectedCustomer);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Customer deleted");
                    alert1.setContentText(selectedCustomer.getCustomerName() + "'s customer record has been deleted.");
                    alert1.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("This customer has existing appointments that must be deleted first.");
                alert.showAndWait();
            }


        }
    }
}

