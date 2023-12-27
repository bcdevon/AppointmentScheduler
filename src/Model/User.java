package Model;

/**This is the User class.
 * This class represents a user with a unique ID and username.*/
public class User {
    private int userID;
    private String username;

    /**This is the User constructor.
     * It constructs a User with the specified userID and username*/
    public User(int userID, String username){
        this.userID = userID;
        this.username = username;
    }

    //Getter and setter methods for each User field.
    public int getUserID(){
        return  userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

