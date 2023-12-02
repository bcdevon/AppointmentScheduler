package Controller;

import DAO.CustomerDAO;
import DAO.CustomerQuery;
import Model.Country;
import Model.Customer;
import helper.CurrentUser;
import helper.JDBC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class add_CustomerController implements Initializable {
    public TextField addIDTF;
    public TextField addNameTF;
    public TextField addAddressTF;
    public TextField addPostalCodeTF;
    public TextField addPhoneTF;
    public ComboBox <Country> countryBox;
    public ComboBox stateBox;
    private int divisionIDS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryBox.setItems(FXCollections.observableList(CustomerDAO.getAllCountries()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stateBox.setItems(CustomerDAO.getAllDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Set initial focus on addNameTF
        Platform.runLater(() -> addNameTF.requestFocus());
    }
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        //get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get input from each text field
//        int idS = Integer.parseInt(addIDTF.getText());
        String nameS = addNameTF.getText();
        String addressS = addAddressTF.getText();
        String postalS = addPostalCodeTF.getText();
        String phoneS = addPhoneTF.getText();
        String createDateS = currentDateTime.format(formatter);
        String createdBy = CurrentUser.getCurrentUser().getUsername();
        String lastUpdatedS = currentDateTime.format(formatter);
        String lastUpdateByS = CurrentUser.getCurrentUser().getUsername();

        int rowsAffected = CustomerQuery.insert(nameS, addressS, postalS, phoneS, createDateS, createdBy, lastUpdatedS, lastUpdateByS, divisionIDS );

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

    public void onCountrySelected(ActionEvent actionEvent) throws SQLException {
        // Get the selected country
        Country selectedCountry = countryBox.getValue();

        if (selectedCountry != null) {
            try {
                // Fetch divisions based on the selected country
                ObservableList<String> divisions = FXCollections.observableList(CustomerDAO.getDivisionsByCountry(selectedCountry));

                // Set divisions to the stateBox
                stateBox.setItems(divisions);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void onDivisionSelected(ActionEvent actionEvent) {
        String selectedDivision = (String) stateBox.getValue();

        if (selectedDivision != null) {
            try {
                //fetch the division id base on the name
                divisionIDS = CustomerDAO.getDivisionIdByName(selectedDivision);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
