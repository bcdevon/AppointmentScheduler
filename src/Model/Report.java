package Model;

/**This is the Report class.
 * This class represents a report containing information about a specific month,
 * type, and customer count. */
public class Report {

    /**The name of the month selected. */
    private String month;

    /**The type of appointment. */
    private String type;

    /**The count of the customers by month and type. */
    private long customerCount;

    /**This is the Report Constructor.
     * It creates a report with a month, type, and customerCount.
     * @param month The name of the month
     * @param type The type of appointment
     * @param customerCount The count of customers by month and type. */
    public Report(String month, String type, long customerCount) {
        this.month = month;
        this.type = type;
        this.customerCount = customerCount;
    }

    //Getters and setters for each Report field

    /**Gets the name of the month.
     *@return The name of the month. */
    public String getMonth() {
        return month;
    }

    /**Sets the name of the month.
     *@param month The name of the month. */
    public void setMonth(String month) {
        this.month = month;
    }

    /**Gets the type of appointment.
     *@return The type of appointment. */
    public String getType() {
        return type;
    }

    /**Sets the type of appointment.
     *@param type The type of appointment. */
    public void setType(String type) {
        this.type = type;
    }

    /**Gets the count of customers by month and type.
     *@return The count of customers by month and type. */
    public long getCount() {
        return customerCount;
    }

    /**Sets the count of customers by month and type.
     * @param customerCount The count of customers by month and type. */
    public void setCount(long customerCount) {
        this.customerCount = customerCount;
    }


}

