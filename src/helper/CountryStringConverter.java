package helper;

import Model.Country;
import javafx.util.StringConverter;

public class CountryStringConverter extends StringConverter<Country> {

    public String toString(Country country) {
        if (country == null) {
            return null;
        }
        return country.getName();
    }


    public Country fromString(String string) {
        // This method is not needed for a ComboBox
        return null;
    }
}
