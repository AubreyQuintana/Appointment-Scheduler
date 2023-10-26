package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * This class holds methods related to the MySQL Database.
 * @author Aubrey Quintana
 */
public class CountryCRUD {

    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * This method retrieves all countries from the MySQL Database.
     * @return List of all countries.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
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

    /**
     * This method uses a Lambda expression to filter all countries by a specific country ID.
     * @param countryID country ID
     * @return First value in List of all countries matching the specific country
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static Country getCountry(int countryID) throws SQLException {
        ObservableList<Country> list = getAllCountries().stream()
                .filter(country -> country.getCountryID() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);
    }

    /**
     * This method allows the user to match the Division ID to its associated Country.
     * @param divisionID division ID
     * @return country name
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static Country getCountryByDivision(int divisionID) throws SQLException {
        FirstLevelDivision div = FirstLevelDivisionCRUD.getDivision(divisionID);
            return getCountry(div.getCountryID());
        }
    }
