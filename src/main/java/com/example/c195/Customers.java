package com.example.c195;

import DAOinterfaces.CustomerDAO;
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
    public TableColumn<Customer, String> customerZipCol;
    @FXML
    public TableColumn<Customer, String> customerPhoneCol;


    public void initialize(URL url, ResourceBundle rb) {

        customerTableView.setItems(CustomerDAO.getAllCustomers());

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

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
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddRecord.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void OnActionUpdateRecord(ActionEvent event) throws IOException {

        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateRecord.fxml"));

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a customer from the list to update");
            alert.showAndWait();
        } else {
            Parent parent = loader.load();
            UpdateRecord addRecord = loader.getController();
            addRecord.sendCustomerInfo(selectedCustomer);
            Scene scene = new Scene(parent, 400, 500);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    public void OnActionDeleteCustomerBtn(ActionEvent event) throws SQLException {

            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Please select a Customer from the list to delete");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm deletion");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                    customerTableView.getItems().remove(selectedCustomer);
                }
            }


        }
    }

