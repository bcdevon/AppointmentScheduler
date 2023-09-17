package Controller;
import helper.JDBC;
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
import java.util.Locale;
import java.util.ResourceBundle;
import helper.JDBC;
public class LoginController implements Initializable {
    public Label theLabel;
    public Button loginButton;
    public Button exitButton;
    public TextField usernameTF;
    public TextField passwordTF;
    public Label timeZoneText;
    public Label userNameText;
    public Label passwordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        ResourceBundle rb = ResourceBundle.getBundle("Main.language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr"))
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        loginButton.setText(rb.getString("Login"));
        exitButton.setText(rb.getString("Exit"));
        timeZoneText.setText(rb.getString("TimeZone"));
        userNameText.setText(rb.getString("username"));
        passwordText.setText(rb.getString("Password"));
        usernameTF.setPromptText(rb.getString("enter") + " " + rb.getString("username"));
        passwordTF.setPromptText(rb.getString("enter") + " " + rb.getString("Password"));
    }

    public void onLoginClicked(ActionEvent actionEvent) throws IOException {
        String enteredUsername = usernameTF.getText();
        String enteredPassword = passwordTF.getText();

        //verify a login and password have been entered and they match.
        //check if username and password are blank
        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            //if password and username is blank display an error and don't login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Enter a passord and username.");
            alert.showAndWait();
            System.out.println("No password or username was entered");
            return;
        }

        //check the entered username and password against the database
        if(authenticateUser(enteredUsername, enteredPassword)){
            //If the do match load the Appointment screen
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
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    private boolean authenticateUser(String username, String password) {
        try {
            // Use PreparedStatement to safely execute the query.
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(
                    "SELECT * FROM users WHERE User_Name = ? AND Password = ?"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the query returned any results.
            return resultSet.next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void onExitClicked(ActionEvent actionEvent) {
        //Get the current application stage and close it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
