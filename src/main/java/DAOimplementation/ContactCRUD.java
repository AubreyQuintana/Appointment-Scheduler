package DAOimplementation;

import DAOinterfaces.ContactDAO;
import model.Contact;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactCRUD {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("CONTACT_ID");
            String contactName = rs.getString("CONTACT_NAME");
            String contactEmail = rs.getString("EMAIL");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            ContactDAO.addContact(contact);
        }
    }
}
