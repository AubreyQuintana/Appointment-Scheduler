package DAOimplementation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class FirstLevelDivisionCRUD {
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
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
    public static FirstLevelDivision getDivision(int divisionID) throws SQLException {
        ObservableList<FirstLevelDivision> list = getAllDivisions().stream()
                .filter(div -> div.getDivisionID() == divisionID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list.get(0);
    }
    public static ObservableList<FirstLevelDivision> getDivisionsByCountry(int countryID) throws SQLException {
        ObservableList<FirstLevelDivision> list = getAllDivisions().stream()
                .filter(div -> div.getCountryID() == countryID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }


}
