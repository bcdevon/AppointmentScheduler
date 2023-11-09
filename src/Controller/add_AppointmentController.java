package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerDAO;
import DAO.CustomerQuery;
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
        addStartTimeBox.setItems(FXCollections.observableArrayList("01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"));
        addEndTimeBox.setItems(FXCollections.observableArrayList("01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"));
        try {
            contactIDComboBox.setItems(CustomerDAO.getAllContactIDs());
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
    }

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {

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
        String creatdateS = "1987-03-20 09:44:22";
        String createdbyS = "Brady";
        String lastupdatedS = "1987-03-20 09:44:22";
        String lastupdatebyS = "Brady";
        int customeridS = (int) customerIDComboBox.getValue();
        int useridS = (int) userIDComboBox.getValue();
        int contactS = (int) contactIDComboBox.getValue();

        int rowsAffected = AppointmentQuery.insert(titleS, descriptionS, locationS, typeS, startS, endS, creatdateS, createdbyS, lastupdatedS, lastupdatebyS, customeridS, useridS, contactS);

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
