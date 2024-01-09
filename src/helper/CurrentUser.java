package helper;
import Model.User;
/**This is the CurrentUser class.
 * This class is used to manage the currently loggin in user.*/
public class CurrentUser {

    /**User field to store the current user*/
    private static User currentUser;

    /**This is the getCurrentUser method.
     * This method gets the currently logged-in user.
     * @return The currently logged-in user.*/
    public static User getCurrentUser() {
        return currentUser;
    }

    /**This is the setCurrentUser method.
     * This method sets the currently logged_in user.
     * @param user The user to set as the currently logged in user.*/
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
