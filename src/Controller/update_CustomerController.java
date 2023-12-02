package Controller;

import DAO.CustomerDAO;
import DAO.CustomerQuery;
import Model.Country;
import helper.CurrentUser;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class update_CustomerController implements Initializable {
    public TextField updateIDTF;
    public TextField updateNameTF;
    public TextField updateAddressTF;
    public TextField updatePostalCodeTF;
    public TextField updatePhoneTF;
    public ComboBox <Country> updateCountryComboBox;
    public ComboBox updateDivisionComboBox;
    private int divisionIDS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Assuming getAllCountries returns a List<Country>
            List<Country> countries = CustomerDAO.getAllCountries();

            // Populate the ComboBox with Country objects
            updateCountryComboBox.setItems(FXCollections.observableList(countries));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            // Assuming getAllDivisions returns a List<String> or something similar
            List<String> divisions = CustomerDAO.getAllDivisions();

            // Populate the ComboBox with Division names
            updateDivisionComboBox.setItems(FXCollections.observableList(divisions));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Platform.runLater(() -> updateNameTF.requestFocus());

//        try {
//            updateCountryComboBox.setItems(FXCollections.observableList(CustomerDAO.getAllCountries()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            updateDivisionComboBox.setItems(FXCollections.observableList(CustomerDAO.getAllDivisions()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        Platform.runLater(() -> updateNameTF.requestFocus());

    }

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




        int rowsAffected = CustomerQuery.update(customerIDS, nameS, addressS, postalS, phoneS, createdateS, createdbyS, lastUpdatedS, lastUpdateByS, divisionIDS);


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


    public void onupdateCountrySelected(ActionEvent actionEvent) {
        Country selectedCountry = updateCountryComboBox.getValue();

        if (selectedCountry != null) {
            try {
                // Fetch divisions based on the selected country
                ObservableList<String> divisions = FXCollections.observableList(CustomerDAO.getDivisionsByCountry(selectedCountry));

                // Set divisions to the stateBox
                updateDivisionComboBox.setItems(divisions);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void onUpdateDivisionSelected(ActionEvent actionEvent) {
        String selectedDivision = (String) updateDivisionComboBox.getValue();

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
