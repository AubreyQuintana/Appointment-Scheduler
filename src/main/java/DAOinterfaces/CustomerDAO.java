package DAOinterfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

public class CustomerDAO {

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static void deleteCustomer(int selectedCustomer){

        allCustomers.forEach(c -> {
            if(c.getCustomerID() == selectedCustomer)
                allCustomers.remove(c);
        });




    }

}
