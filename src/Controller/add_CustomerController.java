package Controller;

import DAO.CustomerDAO;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class add_CustomerController implements Initializable {
    public TextField addIDTF;
    public TextField addNameTF;
    public TextField addAddressTF;
    public TextField addPostalCodeTF;
    public TextField addPhoneTF;
    public ComboBox countryBox;
    public ComboBox stateBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryBox.setItems(CustomerDAO.getAllCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            stateBox.setItems(CustomerDAO.getAllDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void onSave(ActionEvent actionEvent) throws IOException {

        //get input from each text field


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
