package DAOinterfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

public class FirstLevelDivisionDAO {

    private static final ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    public static ObservableList<FirstLevelDivision> getAllDivisions() {

        return allDivisions; }

    public static void addDivision(FirstLevelDivision division){

        allDivisions.add(division);
    }
}
