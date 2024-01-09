package helper;
import java.util.HashMap;
import java.util.Map;

/**This is the CountryStringConverter class.
 * This class provides a way to convert country names to a corresponding integer ID.*/
public class CountryStringConverter {

    /**A map to store country names and their corresponding ID's. */
    private static final Map<String, Integer> countryMap;

    /**Initializes the countryMap with predefined values.
     * Country names are mapped to their corresponding integer IDs. */
    static {
        //map country names to corresponding int ID
        countryMap = new HashMap<>();
        countryMap.put("U.S", 1);
        countryMap.put("UK", 2);
        countryMap.put("Canada", 3);
    }
    /**This is the getcountryAsInt method.
     * This method converts a country name to a country ID.
     * @param country The name of the country to be converted.
     * @return The ID associated with the country name.*/
    public static int getcountryAsInt(String country){
        //retrieve and return the int ID from the countryMap.
        return countryMap.get(country);
    }
}




