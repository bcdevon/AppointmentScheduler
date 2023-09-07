package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onADDAppointment(ActionEvent actionEvent) throws IOException {
        //load Add Appointment screen
        Parent add_Appointment_parent = FXMLLoader.load(getClass().getResource("../View/add_Appointment.fxml"));
        Scene add_Appointment_scene = new Scene(add_Appointment_parent);
        
        //get the current window and set the scene to the add appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(add_Appointment_scene);
        stage.show();
    }

    public void onUpdateAppointment(ActionEvent actionEvent) {
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }
}
