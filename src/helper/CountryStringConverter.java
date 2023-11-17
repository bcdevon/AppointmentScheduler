package helper;
import java.util.HashMap;
import java.util.Map;


public class CountryStringConverter {
    private static final Map<String, Integer> countryMap;
    static {
        //map country names to corresponding int ID
        countryMap = new HashMap<>();
        countryMap.put("U.S", 1);
        countryMap.put("UK", 2);
        countryMap.put("Canada", 3);
    }
    public static int getcountryAsInt(String country){
        return countryMap.get(country);
    }
}




