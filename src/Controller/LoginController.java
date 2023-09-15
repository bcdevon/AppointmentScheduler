package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label theLabel;
    public Button loginButton;
    public Button exitButton;
    public TextField usernameTF;
    public TextField passwordTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    public void onLoginClicked(ActionEvent actionEvent) throws IOException {
        //verify a login and password have been entered and they match.
        //If they don't match display an error
        //If the do match load the Appointment screen
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();

    }

    public void onExitClicked(ActionEvent actionEvent) {
        //Get the current application stage and close it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
