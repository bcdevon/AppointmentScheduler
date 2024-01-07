package Model;

/**This is the LocationReport class.
 * It represents a report containing country, division, and customer count information
 * about a location.*/
public class LocationReport {

    /**The name of the country. */
    private String country;

    /**The name of the division within the country with customers. */
    private String division;

    /**The count of customers associated with the division within the country. */
    private long customerCount;

    /**This is the LocationReport constructor.
     * It creates a new LocationReport with country, division, and customer count.
     * @param country The name of the country.
     * @param division The division within the country.
     * @param customerCount The count of customers associated with the location.*/
    public LocationReport(String country, String division, long customerCount) {
        this.country = country;
        this.division = division;
        this.customerCount = customerCount;
    }

    //Getters and setters for each field in the location report.

    /**Gets the name of the country.
     *@return The name of the country. */
    public String getCountry() {
        return country;
    }

    /**Sets the name of the country.
     *@param country The name of the country. */
    public void setCountry(String country) {
        this.country = country;
    }

    /**Gets the division within the country.
     *@return The division within the country. */
    public String getDivision() {
        return division;
    }

    /**Sets the division within the country.
     *@param division The division within the country. */
    public void setDivision(String division) {
        this.division = division;
    }

    /**Gets the count of customers associated with the location.
     *@return The count of customers associated with the location. */
    public long getCustomerCount() {
        return customerCount;
    }

    /**Sets the count of customers associated with the location.
     *@param customerCount The count of customers associated with the location. */
    public void setCustomerCount(long customerCount) {
        this.customerCount = customerCount;
    }
}
