package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
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

    public void onLoginClicked(ActionEvent actionEvent) {
    }

    public void onExitClicked(ActionEvent actionEvent) {
    }
}
