package DAOinterfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

public class CountryDAO {

    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    public static void addCountry(Country country){
        allCountries.add(country);
    }

}
