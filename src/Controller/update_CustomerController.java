package Controller;

import DAO.CustomerQuery;
import helper.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class update_CustomerController {
    public TextField updateIDTF;
    public TextField updateNameTF;
    public TextField updateAddressTF;
    public TextField updatePostalCodeTF;
    public TextField updatePhoneTF;
    public ComboBox updateCountryComb0Box;
    public ComboBox updateDivisionComboBox;

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        int customerIDS = Integer.parseInt(updateIDTF.getText());
        String nameS = updateNameTF.getText();
        String addressS = updateAddressTF.getText();
        String postalS = updatePostalCodeTF.getText();
        String phoneS = updatePhoneTF.getText();
        String createdateS = currentDateTime.format(formatter);
        String createdbyS = CurrentUser.getCurrentUser().getUsername();
        String lastUpdatedS = formattedDateTime;
        String lastUpdateByS = CurrentUser.getCurrentUser().getUsername();
        String countryS = (String) updateCountryComb0Box.getValue();
        String divisionS = (String) updateDivisionComboBox.getValue();

        int rowsAffected = CustomerQuery.update(customerIDS, nameS, addressS, postalS, phoneS, createdateS, createdbyS, lastUpdatedS, lastUpdateByS, countryS, divisionS);


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
