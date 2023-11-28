package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserLog {

    private static final String LOG_FILE_PATH =  "login_activity.txt";

    public static void loginAttempt(String username, boolean isSuccess) {
        String logMessage = buildLogMessage(username, isSuccess);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            writer.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    private static String buildLogMessage(String username, boolean isSuccess) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        String status = isSuccess ? "Success" : "Failure";

        return String.format("%s - User: %s, Log In Attempt: %s", timestamp, username, status);
    }
}
