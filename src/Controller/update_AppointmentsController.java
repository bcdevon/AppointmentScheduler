package Controller;

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

public class update_AppointmentsController {
    public TextField updateAppointmentIDTF;
    public TextField updateTitleTF;
    public TextField updateDescriptionTF;
    public TextField updateLocationTF;
    public TextField updateContactTF;
    public TextField updateStartTime;
    public TextField updateTypeTF;
    public TextField updateEndTime;
    public TextField updateCustomerIDTF;
    public TextField updateUserIDTF;
    public Button saveUpdateButton;
    public Button cancelUpdateButton;
    public DatePicker updateStartDate;
    public DatePicker updateEndDate;

    public void onSave(ActionEvent actionEvent) {
        //get appointment updates
        String updateTitle = updateTitleTF.getText();
        String updateDescription = updateDescriptionTF.getText();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    public void onSaveUpdate(ActionEvent actionEvent) {
    }

    public void onCancelUpdate(ActionEvent actionEvent) throws IOException {
        //return to appointment screen without updating
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }
}
