package Model;

/**This is the Report class.
 * This class represents a report containing information about a specific month,
 * type, and customer count.*/
public class Report {
    private String month;
    private String type;
    private long customerCount;

    /**This is the Report Constructor.
     * It creates a report with a month, type, and customerCount.
     * @param month The name of the month
     * @param type The type of appointment
     * @param customerCount The count of customers by month and type.*/
    public Report(String month, String type, long customerCount) {
        this.month = month;
        this.type = type;
        this.customerCount = customerCount;
    }

    //Getters and setters for each Report field
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return customerCount;
    }

    public void setCount(long customerCount) {
        this.customerCount = customerCount;
    }


}

