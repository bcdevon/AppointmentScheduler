package Main;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Label theLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    public void OnButtonClicked(ActionEvent actionEvent) {
        theLabel.setText("You clicked me");
        System.out.println("The button was clicked");
    }
}
