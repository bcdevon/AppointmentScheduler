package Model;

/**This is the Customer class.
 * This class represents a Customer in the application.*/
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int divisionId;
    private String division;
    private String country;

    /**This is the constructor to create a new customer.
     * @param id The ID of the customer
     * @param name The name of the customer
     * @param address The address of the customer
     * @param postal The postal code of the customer
     * @param phone The phone number of the customer
     * @param divisionId The ID of the division associated with the customer
     * @param division The name of the division associated with the customer
     * @param country The country associated with the customer*/
    public Customer(int id, String name, String address, String postal, String phone, int divisionId, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;

    }

    /**This is the default constructor for the customer class.*/
    public Customer() {
    }

    //Getter and setter method for each customer field.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {return country; }

    public void setCountry(String country) {
        this.country = country;
    }
}
