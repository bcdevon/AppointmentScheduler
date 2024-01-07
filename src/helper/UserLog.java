package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**This is the UserLog class.
 * This class provides methods for recording user login attempts.*/
public class UserLog {

    //The path to the log file where the login attempts will be recorded
    /**The constant `LOG_FILE_PATH` represents the path to the log file where login attempts will be recorded. */
    private static final String LOG_FILE_PATH =  "login_activity.txt";
    /**This is the loginAttempt method.
     * This method logs a user login attempt by adding a message to the log file.
     * @param username The username attempting to login.
     * @param isSuccess A boolean indicating if the login attempt was successful.*/
    public static void loginAttempt(String username, boolean isSuccess) {
        //building the log message based on the username and login success status
        String logMessage = buildLogMessage(username, isSuccess);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            //Write the log message to the log file
            writer.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**This is the buildLogMessage method.
     * This method builds the log message to be recorded in the log file.
     * @param username The username attempting to log in.
     * @param isSuccess A boolean indicating if the login attempt was successful.
     * @return The formatted log message.*/
    private static String buildLogMessage(String username, boolean isSuccess) {
        //Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        //Format the timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        //Determine the login status
        String status = isSuccess ? "Success" : "Failure";
        //Format and return the complete log message
        return String.format("%s - User: %s, Log In Attempt: %s", timestamp, username, status);
    }
}
