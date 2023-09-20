package DAOimplementation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.FirstLevelDivision;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ContactCRUD {

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        if (allContacts.isEmpty()) {
            String sql = "SELECT * FROM CONTACTS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactID = rs.getInt("CONTACT_ID");
                String contactName = rs.getString("CONTACT_NAME");
                String contactEmail = rs.getString("EMAIL");
                Contact contact = new Contact(contactID, contactName, contactEmail);
                allContacts.add(contact);
            }
        }
        return allContacts;
    }

    public static Contact getContact(int contactID) throws SQLException {
        ObservableList<Contact> list = getAllContacts().stream()
                .filter(div -> div.getContactID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);

    /*@Override
    public String toString () {
        return contactName;
    }*/
    }
}
