package Model;

/**This is the Country class.
 * This class represents a Country with and ID and name.*/
public class Country {
    private int id;
    private String name;

    //Getters and setter methods for each Country field
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

    /**This is the Country constructor
     * It constructs a Country object with an ID and name.
     * @param id The ID of the country.
     * @param name The name of the country.*/
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**This is the toString method.
     * This method returns the name of the country as a String.
     * @return The name of the country.*/
    public String toString(){
        return name;
    }
}
