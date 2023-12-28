package Controller;
import Model.User;
import helper.CurrentUser;
import helper.JDBC;
import helper.UserLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import helper.JDBC;

/**This is the loginController class.
 * This class handles user authentication and navigation to the main application screen.
 * */
public class LoginController implements Initializable {
    public Button loginButton;
    public Button exitButton;
    public TextField usernameTF;
    public TextField passwordTF;
    public Label timeZoneText;
    public Label userNameText;
    public Label passwordText;
    public TextField zoneIDTF;
    public TextField dateTimeTF;

    /**This is the initialize method.
     * This method is called during initialization and sets up localization, default values,
     * and the current date and time.
     * @param url  The location of the Login.fxml
     * @param resourceBundle resources used for initialization*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        //load localization resources
        ResourceBundle rb = ResourceBundle.getBundle("Main.language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr"))
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        //Set the labels and buttons text based on localization.
        loginButton.setText(rb.getString("Login"));
        exitButton.setText(rb.getString("Exit"));
        timeZoneText.setText(rb.getString("TimeZone"));
        userNameText.setText(rb.getString("username"));
        passwordText.setText(rb.getString("Password"));
        usernameTF.setPromptText(rb.getString("enter") + " " + rb.getString("username"));
        passwordTF.setPromptText(rb.getString("enter") + " " + rb.getString("Password"));

        //Set the zoneIDTF to the user current zoneID
        zoneIDTF.setText(ZoneId.systemDefault().getId());

        //get the current date and time
        Date currentDate = new Date();

        //Format the date and time as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDatetime = dateFormat.format(currentDate);
        //set the text field to the formatted date time
        dateTimeTF.setText(formattedDatetime);
    }

    /**This is the onLoginClicked method.
     * This is an event handler method that is called when the login button is clicked.
     * It authenticates the user and navigates to the main application screen or
     * displays an error message if username and password don't match.
     * @param actionEvent The event triggered when the login button is clicked
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public void onLoginClicked(ActionEvent actionEvent) throws IOException {
        String enteredUsername = usernameTF.getText();
        String enteredPassword = passwordTF.getText();
        //load the localization resources
        ResourceBundle rb = ResourceBundle.getBundle("Main.language", Locale.getDefault());
        //log the attempted login
        UserLog.loginAttempt(enteredUsername, authenticateUser(enteredUsername, enteredPassword));

        //verify a login and password have been entered and they match.
        //check if username and password are blank
        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            //if password and username is blank display an error and don't login.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(rb.getString("enter") + " " + rb.getString("username") + " " + rb.getString("and") + " " + rb.getString("Password"));
            alert.showAndWait();
            System.out.println("No password or username was entered");
            return;
        }

        //check the entered username and password against the database
        if(authenticateUser(enteredUsername, enteredPassword)){

            // Fetch user information from the database
            User authenticatedUser = getUserByUsername(enteredUsername);

            // Set the authenticated user as the current user
            CurrentUser.setCurrentUser(authenticatedUser);

            //If they do match load the Appointment screen
            Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
            Scene appointment_scene = new Scene(appointment_parent);

            //Get the current window and set the scene to the appointment scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(appointment_scene);
            stage.show();
        }else {
            // Display an error message if authentication fails.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(rb.getString("invalid") + " " + rb.getString("username") + " " + rb.getString("and") + " " + rb.getString("Password"));
            alert.showAndWait();
        }
    }

    /**This is the getUserByUsername method.
     * This method gets a user from the database based on the provided username.
     * @param username the username of the user to retrieve
     * @return The user object if found.*/
    private User getUserByUsername(String username) {
        try {
            // Use PreparedStatement to safely execute the query.
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(
                    "SELECT * FROM users WHERE User_Name = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the query returned any results.
            if (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                //return user if found
                return new User(userId, username);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        //return nothing if user not found
        return null;
    }

    /**This is the authenticateUser method.
     * This method authenticates a user based on the provided username and password.
     * @param username the username to authenticate.
     * @param password the password to authenticate.
     * @return True if the authentication passes else return false.*/
    private boolean authenticateUser(String username, String password) {
        try {
            //Check for user with provided username and password.
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(
                    "SELECT * FROM users WHERE User_Name = ? AND password = ?"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            //execute query and retrieve results
            ResultSet resultSet = preparedStatement.executeQuery();

            // If any results are returned then it is True user authenticated.
            return resultSet.next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            //If no results ar returned then it is False user not authenticated.
            return false;
        }
    }

    /**This is the onExitClicked method.
     * This is an event handler method that is called when the user clicks exit.
     * When the exit button is pressed the application closes.
     * @param actionEvent The event triggered when the exit button is clicked.*/
    public void onExitClicked(ActionEvent actionEvent) {
        //Get the current application stage and close it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}
