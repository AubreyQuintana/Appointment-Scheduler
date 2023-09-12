package DAOinterfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

public class ContactDAO {

    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public static ObservableList<Contact> getAllContacts() { return allContacts; }

    public static void addContact(Contact contact){ allContacts.add(contact); }


    /*@Override
    public String toString () {
        return contactName;
    }*/

}
