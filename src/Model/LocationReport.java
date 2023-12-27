package Model;

/**This is the LocationReport class.
 * It represents a report containing country, division, and customer count information
 * about a location.*/
public class LocationReport {
    private String country;
    private String division;
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
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(long customerCount) {
        this.customerCount = customerCount;
    }
}
