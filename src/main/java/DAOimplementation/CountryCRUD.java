package DAOimplementation;

import DAOinterfaces.CountryDAO;
import model.Country;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryCRUD {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryID = rs.getInt("COUNTRY_ID");
            String countryName = rs.getString("COUNTRY");
            Country country = new Country(countryID, countryName);
            CountryDAO.addCountry(country);
        }
    }

}
