package helper;
import java.util.HashMap;
import java.util.Map;

/**This is the MonthConverter class.
 * This class is used for converting month names to corresponding integers.*/
public class MonthConverter {

    /**A map to store month names and their corresponding integers. */
    private static final Map<String, Integer> monthMap;
    /**Initializes the monthMap with predefined values.
     * The keys are month names the values are corresponding integers. */
    static {
        //map month names to corresponding int
        monthMap = new HashMap<>();
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);
    }
    /**This is the getMonthAsint method.
     * This method converts a month name to its corresponding integer.
     * @param month The name of the month.
     * @return The integer representation of the month. */
    public static int getMonthAsInt(String month) {
        //retrieve and return the corresponding integer from the monthMap
        return monthMap.get(month);
    }

}
