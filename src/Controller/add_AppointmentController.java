package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerDAO;
import DAO.CustomerQuery;
import Model.User;
import helper.CurrentUser;
import javafx.application.Platform;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class add_AppointmentController implements Initializable {
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
    public ComboBox addStartTimeBox;
    public ComboBox addEndTimeBox;
    public ComboBox contactIDComboBox;
    public ComboBox customerIDComboBox;
    public ComboBox userIDComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStartTimeBox.setItems(FXCollections.observableArrayList("01:00", "01:15","01:30","01:45", "02:00","02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45",  "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45","08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45","10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00","13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45","21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45", "24:00", "24:15", "24:30", "24:45"));
        addEndTimeBox.setItems(FXCollections.observableArrayList("01:00", "01:15","01:30","01:45", "02:00","02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45",  "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45","08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45","10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00","13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45","21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45", "24:00", "24:15", "24:30", "24:45"));
        try {
            contactIDComboBox.setItems(CustomerDAO.getAllContactNames());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            customerIDComboBox.setItems(CustomerDAO.getAllCustomerIDs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            userIDComboBox.setItems(CustomerDAO.getAllUserIDs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set initial focus on addTitleTF
        Platform.runLater(() -> addTitleTF.requestFocus());
    }

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {

        //get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        //combine selected date and time to create start and end date/time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = addStartDate.getValue().toString();
        String startTime = addStartTimeBox.getValue().toString() + ":00";
        String endDate = addEndDate.getValue().toString();
        String endTime = addEndTimeBox.getValue().toString() + ":00";
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " " + startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate+ " " + endTime, formatter);

       // Define your time zone
        ZoneId localTimeZone = ZoneId.systemDefault();

// Create ZonedDateTime objects with the local time and your time zone
        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, localTimeZone);
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, localTimeZone);

// Convert to UTC
        ZonedDateTime startZonedDateTimeUTC = startZonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endZonedDateTimeUTC = endZonedDateTime.withZoneSameInstant(ZoneOffset.UTC);


        String titleS = addTitleTF.getText();
        String descriptionS = addDescriptionTF.getText();
        String locationS = addLocationTF.getText();
        String typeS = addTypeTF.getText();
        String startS = startZonedDateTimeUTC.format(formatter);
        String endS = endZonedDateTimeUTC.format(formatter);
        String creatdateS = currentDateTime.format(formatter);
        String createdbyS = CurrentUser.getCurrentUser().getUsername();
        String lastupdatedS = startZonedDateTimeUTC.format(formatter);
        String lastupdatebyS = CurrentUser.getCurrentUser().getUsername();
        int customeridS = (int) customerIDComboBox.getValue();
        int useridS = (int) userIDComboBox.getValue();
        String contactName = (String) contactIDComboBox.getValue();
        int contactS = AppointmentQuery.getContactIDByName(contactName);
        int rowsAffected = AppointmentQuery.insert(titleS, descriptionS, locationS, typeS, startS, endS, creatdateS, createdbyS, lastupdatedS, lastupdatebyS, useridS, customeridS, contactS);

        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
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
