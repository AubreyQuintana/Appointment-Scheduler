package DAOimplementation;

import DAOinterfaces.FirstLevelDivisionDAO;
import model.FirstLevelDivision;
import utility.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionCRUD {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = DatabaseConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("DIVISION_ID");
            String divisionName = rs.getString("DIVISION");
            int countryID = rs.getInt("COUNTRY_ID");
            FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, countryID);
            FirstLevelDivisionDAO.addDivision(division);
        }
    }
}
