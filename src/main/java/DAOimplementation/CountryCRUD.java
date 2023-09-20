package DAOimplementation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class CountryCRUD {

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        if (allCountries.isEmpty()) {
            String sql = "SELECT * FROM COUNTRIES";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("COUNTRY_ID");
                String countryName = rs.getString("COUNTRY");
                Country country = new Country(countryID, countryName);
                allCountries.add(country);
            }
        }
        return allCountries;
    }

    public static Country getCountry(int countryID) throws SQLException {
        ObservableList<Country> list = getAllCountries().stream()
                .filter(country -> country.getCountryID() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);
    }

    public static Country getCountryByDivision(int divisionID) {

        try {
            FirstLevelDivision div = FirstLevelDivisionCRUD.getDivision(divisionID);
            return getCountry(div.getCountryID());
        } catch (SQLException e) {
            return null;
        }
    }
}
