package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerQuery;
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

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        String titleS = addTitleTF.getText();
        String descriptionS = addDescriptionTF.getText();
        String locationS = addLocationTF.getText();
        String typeS = addTypeTF.getText();
        String startS = "2023-10-20 09:44:22";
        String endS = "2023-10-22 09:44:22";
        String creatdateS = "1987-03-20 09:44:22";
        String createdbyS = "Brady";
        String lastupdatedS = "1987-03-20 09:44:22";
        String lastupdatebyS = "Brady";
        int customeridS = Integer.parseInt(addCustomerIDTF.getText());
        int useridS = Integer.parseInt(addUserIDTF.getText());
        int contactS = Integer.parseInt(addContactTF.getText());

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
