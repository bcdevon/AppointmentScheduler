package Controller;

import DAO.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class add_AppointmentController {
    public TextField addAppointmentTF;
    public TextField addTitleTF;
    public TextField addDescriptionTF;
    public TextField addLocationTF;
    public TextField addContactTF;
    public TextField addStartTimeTF;
    public TextField addTypeTF;
    public TextField addEndTimeTF;
    public TextField addCustomerIDTF;
    public TextField addUserIDTF;
    public DatePicker addStartDate;
    public DatePicker addEndDate;
    public Button saveButton;

    public void onSave(ActionEvent actionEvent) throws SQLException {
        AppointmentQuery.select();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }
}
