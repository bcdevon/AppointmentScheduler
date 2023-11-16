package Model;

public class LocationReport {
    private String country;
    private String division;
    private long customerCount;

    public LocationReport(String country, String division, long customerCount) {
        this.country = country;
        this.division = division;
        this.customerCount = customerCount;
    }

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
