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
        updateStartTimeComboBox.setItems(FXCollections.observableArrayList("01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"));
        updateEndTimeComboBox.setItems(FXCollections.observableArrayList("01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"));

        try {
            updateContactComboBox.setItems(CustomerDAO.getAllContactNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            updateUserIDComboBox.setItems(CustomerDAO.getAllUserIDs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            updateCustomerIDComboBox.setItems(CustomerDAO.getAllCustomerIDs());
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
