package Controller;

import DAO.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class update_AppointmentsController implements Initializable {
    public TextField updateAppointmentIDTF;
    public TextField updateTitleTF;
    public TextField updateDescriptionTF;
    public TextField updateLocationTF;
    public TextField updateTypeTF;
    public Button saveUpdateButton;
    public Button cancelUpdateButton;
    public DatePicker updateStartDate;
    public DatePicker updateEndDate;
    
    public ComboBox updateCustomerIDComboBox;
    public ComboBox updateUserIDComboBox;
    public ComboBox updateStartTimeComboBox;
    public ComboBox updateEndTimeComboBox;
    public ComboBox updateContactComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateContactComboBox.setItems(CustomerDAO.getAllContactNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSave(ActionEvent actionEvent) {
        //get appointment updates
        String updateTitle = updateTitleTF.getText();
        String updateDescription = updateDescriptionTF.getText();
        String updateLocation = updateLocationTF.getText();
        String updateType = updateTypeTF.getText();

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
