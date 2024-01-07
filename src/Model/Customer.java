package Model;

/**This is the Customer class.
 * This class represents a Customer in the application.*/
public class Customer {

    /**The unique ID of the customer. */
    private int id;

    /**The name of the customer. */
    private String name;

    /**The Address of the customer. */
    private String address;

    /**The postal code of the customer. */
    private String postal;

    /**The phone number of the customer. */
    private String phone;

    /**The division ID associated with the customer. */
    private int divisionId;

    /**The name of the division associated with the customer. */
    private String division;

    /**The country associated with the customer. */
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

    /**Gets the ID of the customer.
     * @return The ID of the customer. */
    public int getId() {
        return id;
    }

    /**Sets the ID of the customer.
     * @param id The ID of the customer.*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets the name of the customer.
     * @return The name of the customer. */
    public String getName() {
        return name;
    }

    /**Sets the name of the customer.
     * @param name The name of the customer. */
    public void setName(String name) {
        this.name = name;
    }

    /**Gets the address of the customer.
     * @return The address of the customer. */
    public String getAddress() {
        return address;
    }

    /**Sets the address of the customer.
     *@param address The address of the customer. */
    public void setAddress(String address) {
        this.address = address;
    }

    /**Gets the postal code of the customer.
     *@return The postal code of the customer. */
    public String getPostal() {
        return postal;
    }

    /**Sets the postal code of the customer.
     *@param postal The postal code of the customer. */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**Gets the phone number of the customer.
     *@return The phone number of the customer. */
    public String getPhone() {
        return phone;
    }

    /**Sets the phone number of the customer.
     *@param phone The phone number of the customer. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**Gets the ID of the division associated with the customer.
     *@return The ID of the division associated with the customer. */
    public int getDivisionId() {
        return divisionId;
    }

    /**Sets the ID of the division associated with the customer.
     *@param divisionId The ID of the division associated with the customer. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**Gets the name of the division associated with the customer.
     *@return The name of the division associated with the customer. */
    public String getDivision() {
        return division;
    }

    /**Sets the name of the division associated with the customer.
     *@param division The name of the division associated with the customer. */
    public void setDivision(String division) {
        this.division = division;
    }

    /**Gets the country associated with the customer.
     *@return The country associated with the customer. */
    public String getCountry() {return country; }

    /**Sets the country associated with the customer.
     *@param country The country associated with the customer. */
    public void setCountry(String country) {
        this.country = country;
    }
}
