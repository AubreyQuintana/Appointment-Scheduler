package com.example.c195;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import objectCRUDs.AppointmentsCRUD;
import objectCRUDs.ContactCRUD;
import objectCRUDs.CountryCRUD;
import objectCRUDs.CustomerCRUD;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class represents your Reports page.
 * @author Aubrey Quintana
 */
public class Reports implements Initializable {
    @FXML
    public Button okBtn;
    @FXML
    public ComboBox<Month> monthCBox;
    @FXML
    public ComboBox<String> typeCBox;
    @FXML
    public TextField displayBox;
    @FXML
    public TableView<Appointment> contactTableView;
    @FXML
    public TableColumn<Contact, Integer> apptIDCol;
    @FXML
    public TableColumn<Contact, String> titleCol;
    @FXML
    public TableColumn<Contact, String> typeCol;
    @FXML
    public TableColumn<Contact, String> DescCol;
    @FXML
    public TableColumn<Contact, LocalDateTime> startCol;
    @FXML
    public TableColumn<Contact, LocalDateTime> endCol;
    @FXML
    public TableColumn<Contact, Integer> customerIDCol;
    @FXML
    public ComboBox<Contact> contactCBox;
    @FXML
    public ListView<Customer> customerListView;
    @FXML
    public ComboBox<Country> countryCBox;
    @FXML
    public Button backBtn;

    /**
     * This method is used to initialize any data that needs to be run first.
     * Such as, populating combo boxes and tableviews.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //REPORT INFORMATION TO FILTER BY TYPE AND MONTH
        try {
            typeCBox.setItems(AppointmentsCRUD.getTypeOfAppointment());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            monthCBox.setItems(AppointmentsCRUD.getMonthOfAppointment());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //REPORT INFORMATION TO VIEW CONTACTS APPOINTMENTS
        try {
            contactCBox.setItems(ContactCRUD.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        DescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        try {
            contactTableView.setItems(AppointmentsCRUD.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //REPORT INFORMATION TO GET CUSTOMERS BASED ON COUNTRY
        try {
            countryCBox.setItems(CountryCRUD.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method populates the contact table view based on combo box selection.
     * @param event Event occurs when user chooses a contact from the combo box.
     * @throws SQLException Throws exception when errors connecting to the MySQL database.
     */
    public void contactCBoxEvent(ActionEvent event) throws SQLException {

        Contact contact = contactCBox.getValue();
        int contactID = contact.getContactID();
        contactTableView.setItems(AppointmentsCRUD.getAppointmentsByContact(contactID));
    }

    /**
     * This method populates the contact tableview with all data.
     * @param event Event occurs when the View All button is clicked.
     * @throws SQLException Throws exception when errors connecting to the MySQL database.
     */
    public void viewAllBtn(ActionEvent event) throws SQLException {

        contactTableView.setItems(AppointmentsCRUD.getAllAppointments());
    }

    /**
     * This method displays a value in the display box based off the user's combo box selections.
     * @param event Event occurs when clicks on the OK button.
     * @throws SQLException Throws exception when errors connecting to the MySQL database.
     */
    public void numAptBtn(ActionEvent event) throws SQLException {

        String type = typeCBox.getValue();
        Month month = monthCBox.getValue();

        int number = AppointmentsCRUD.getAppointmentsByTypeAndMonth(type, month).size();

        displayBox.setText(String.valueOf(number));

    }

    /**
     * This method populates the list view based off user's combo box selection.
     * @param event Event occurs when user chooses a Country from the combo box.
     * @throws SQLException Throws exception when errors connecting to the MySQL database.
     */
    public void OnActionCountryCBox(ActionEvent event) throws SQLException {

        Country country = countryCBox.getValue();
        String countryName = country.getCountry();

        customerListView.setItems(CustomerCRUD.getCustomersByCountry(countryName));

    }

    /**
     * This method allows the user to navigate back to the Choose page.
     * @param event Event occurs when user clicks on the Back button.
     * @throws IOException Throws exception when errors connecting to the MySQL database.
     */
    public void OnActionBackBtn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Choose.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
