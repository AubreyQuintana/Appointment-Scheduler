package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utility.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * This class holds methods related to the MySQL Database.
 * @author Aubrey Quintana
 */
public class CustomerCRUD {

    /**
     * This method allows insertion of Customers into the database.
     * @param customerName The customer name
     * @param address The customer's address
     * @param postalCode The customer's zipcode
     * @param phoneNumber The customer's phone number
     * @param divisionID The customer's division ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static void insert(String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {

        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME, ADDRESS, POSTAL_CODE, PHONE, DIVISION_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        int rowsAffected = ps.executeUpdate();
        //return rowsAffected;
    }

    /**
     * This method allows the update to data in the Customer table of the MySQL database.
     * @param customerID The customer ID
     * @param customerName The customer name
     * @param address The customer's address
     * @param postalCode The customer's zipcode
     * @param phoneNumber The customer's phone number
     * @param divisionID The customer's division ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static void update(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PHONE = ?, DIVISION_ID = ? WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        int rowsAffected = ps.executeUpdate();
    }

    /**
     * This method allows the deletion of a specific customer from the MySQL Database.
     * @param customerID customer ID
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static void delete(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
    }

    /**
     * This method retrieves all information for a specific customer from multiple tables in the MySQL Database.
     * @param customerID customer ID
     * @return Customer Object containing all parameters for Customer class.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static Customer getCustomer(int customerID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS, FIRST_LEVEL_DIVISIONS, COUNTRIES WHERE CUSTOMER_ID = ? AND " +
                "CUSTOMERS.DIVISION_ID = FIRST_LEVEL_DIVISIONS.DIVISION_ID AND " +
                "FIRST_LEVEL_DIVISIONS.COUNTRY_ID = COUNTRIES.COUNTRY_ID";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerIDFK = rs.getInt("CUSTOMER_ID");
            String customerName = rs.getString("CUSTOMER_NAME");
            String address = rs.getString("ADDRESS");
            String postalCode = rs.getString("POSTAL_CODE");
            String phoneNumber = rs.getString("PHONE");
            int divisionID = rs.getInt("DIVISION_ID");
            String divisionName = rs.getString("DIVISION");
            String countryName = rs.getString("COUNTRY");
            Customer customerResult = new Customer(customerIDFK, customerName, address, postalCode, phoneNumber, divisionID, divisionName, countryName);
            return customerResult;
        }
        return null;
    }

    /**
     * This method retrieves all customer information from multiple tables in the MySQL Database.
     * @return List of all Customers
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        if (allCustomers.isEmpty()) {
            String sql = "SELECT * FROM CUSTOMERS, FIRST_LEVEL_DIVISIONS, " +
                    "COUNTRIES WHERE CUSTOMERS.DIVISION_ID = FIRST_LEVEL_DIVISIONS.DIVISION_ID AND " +
                    "FIRST_LEVEL_DIVISIONS.COUNTRY_ID = COUNTRIES.COUNTRY_ID ORDER BY CUSTOMERS.CUSTOMER_ID";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("CUSTOMER_ID");
                String customerName = rs.getString("CUSTOMER_NAME");
                String address = rs.getString("ADDRESS");
                String postalCode = rs.getString("POSTAL_CODE");
                String phoneNumber = rs.getString("PHONE");
                int divisionID = rs.getInt("DIVISION_ID");
                String divisionName = rs.getString("DIVISION");
                String countryName = rs.getString("COUNTRY");
                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, divisionID, divisionName, countryName);
                allCustomers.add(customer);
            }
        }
        return allCustomers;
    }

    /**
     * This method uses a Lambda expression to filter all customers associated with a specific country.
     * @param country country name
     * @return List of all customers associated with the specified country.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Customer> getCustomersByCountry(String country) throws SQLException {
        ObservableList<Customer> list = getAllCustomers().stream()
                .filter(customer -> customer.getCountryName().equals(country))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }
}
