package objectCRUDs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class FirstLevelDivisionCRUD {

    private static final ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    /**
     * This method retrieves all divisions from the MySQL Database.
     * @return List of all divisions
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        if (allDivisions.isEmpty()) {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("DIVISION_ID");
                String divisionName = rs.getString("DIVISION");
                int countryID = rs.getInt("COUNTRY_ID");
                FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, countryID);
                allDivisions.add(division);
            }
        }
        return allDivisions;
    }

    //Lamda expression - EXPLAIN

    /**
     * This method uses a Lambda expression to filter all divisions by a specific division ID.
     * @param divisionID division ID
     * @return First value in List of all divisions matching the specific division.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static FirstLevelDivision getDivision(int divisionID) throws SQLException {
        ObservableList<FirstLevelDivision> list = getAllDivisions().stream()
                .filter(div -> div.getDivisionID() == divisionID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);
    }

    /**
     * This method uses a Lambda expression to filter all divisions by a specific country.
     * @param countryID country ID
     * @return List of all divisions associated with specified country.
     * @throws SQLException Throws exception if error connecting to the MySQL Database.
     */
    public static ObservableList<FirstLevelDivision> getDivisionsByCountry(int countryID) throws SQLException {
        ObservableList<FirstLevelDivision> list = getAllDivisions().stream()
                .filter(div -> div.getCountryID() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

}
