package DAOimplementation;

import DAOinterfaces.CustomerDAO;
import model.Customer;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerCRUD {

    public static int counter = CustomerDAO.getAllCustomers().size();

    //CRUD FOR CUSTOMERS TABLE
    public static int insert(String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {

        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME, ADDRESS, POSTAL_CODE, PHONE, DIVISION_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void update(int customerID, String customerName, String address, String phoneNumber, String zipCode) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, PHONE = ?, POSTAL_CODE = ? WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, phoneNumber);
        ps.setString(4, zipCode);
        ps.setInt(5, customerID);
        int rowsAffected = ps.executeUpdate();
    }

    public static void delete(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS"; //WHERE PARAMETER = ? THEN USE SETTER TO DEFINE BIND VARIABLE, NAME VARIABLE NAMEFK
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("CUSTOMER_ID");
            String customerName = rs.getString("CUSTOMER_NAME");
            String address = rs.getString("ADDRESS");
            String postalCode = rs.getString("POSTAL_CODE");
            String phoneNumber = rs.getString("PHONE");
            int divisionID = rs.getInt("DIVISION_ID");
            //System.out.print(customerID + customerName + address + postalCode + phoneNumber + divisionID + "\n");
            Customer customer = new Customer (customerID, customerName, address, postalCode, phoneNumber, divisionID);
            CustomerDAO.addCustomer(customer);
        }
    }

}
