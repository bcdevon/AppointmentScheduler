package Model;

/**This is the User class.
 * This class represents a user with a unique ID and username.*/
public class User {

    /**The unique ID of the user. */
    private int userID;

    /**The username of the user. */
    private String username;

    /**This is the User constructor.
     * It constructs a User with the specified userID and username*/
    public User(int userID, String username){
        this.userID = userID;
        this.username = username;
    }

    //Getter and setter methods for each User field.

    /**Gets the unique ID of the user.
     *@return The unique ID of the user. */
    public int getUserID(){
        return  userID;
    }

    /**Sets the unique ID of the user.
     *@param userID The unique ID of the user. */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**Gets the username of the user.
     *@return The username of the user. */
    public String getUsername(){
        return username;
    }

    /**Sets the username of the user.
     *@param username The username of the user. */
    public void setUsername(String username) {
        this.username = username;
    }

}

