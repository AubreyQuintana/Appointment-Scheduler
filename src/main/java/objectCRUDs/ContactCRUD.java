package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * This class holds methods related to the MySQL Database.
 * @author Aubrey Quintana
 */
public class ContactCRUD {

    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * This method retrieves all contacts from the MySQL Database.
     * @return List of all contacts.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
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

    //Get contact info to populate contact combo boxes

    /**
     * This method uses a Lambda expression to filter all contacts by a specific contact
     * @param contactID contact ID
     * @return First value in List of all contacts matching the specific contact
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static Contact getContact(int contactID) throws SQLException {
        ObservableList<Contact> list = getAllContacts().stream()
                .filter(div -> div.getContactID() == contactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);
    }
}
