package helper;
import java.util.HashMap;
import java.util.Map;

public class MonthConverter {
    private static final Map<String, Integer> monthMap;
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
    public static int getMonthAsInt(String month) {


        return monthMap.get(month);
    }

}
