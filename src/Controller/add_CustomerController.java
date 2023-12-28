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

/**This is the add_CustomerController class.
 * This class handles adding customers, save or cancel when a customer is added.
 * It also manages values in text fields and combo boxes*/
public class add_CustomerController implements Initializable {
    public TextField addIDTF;
    public TextField addNameTF;
    public TextField addAddressTF;
    public TextField addPostalCodeTF;
    public TextField addPhoneTF;
    public ComboBox <Country> countryBox;
    public ComboBox stateBox;
    private int divisionIDS;

    /**This is the initialize method.
     * This method is call during initialization and sets up the available value options for the combo boxes.
     * @param url The location of the FXML file
     * @param resourceBundle Resources used for initialization*/
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

    /**This is the onSave method.
     * This is an event handler method that is called when the user clicks the save button to save an added Customer.
     * It gets values from the text fields, and combo Boxes it formats and converts the time and date.
     * It also gets information about the user who created the customer and the time it was created.
     * @param actionEvent  The event triggered when the save button is clicked.
     * @throws SQLException If a SQL exception occurs during the database operation.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        //get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get input from each text field
        String nameS = addNameTF.getText();
        String addressS = addAddressTF.getText();
        String postalS = addPostalCodeTF.getText();
        String phoneS = addPhoneTF.getText();
        String createDateS = currentDateTime.format(formatter);
        String createdBy = CurrentUser.getCurrentUser().getUsername();
        String lastUpdatedS = currentDateTime.format(formatter);
        String lastUpdateByS = CurrentUser.getCurrentUser().getUsername();

        int rowsAffected = CustomerQuery.insert(nameS, addressS, postalS, phoneS, createDateS, createdBy, lastUpdatedS, lastUpdateByS, divisionIDS );

        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    /**This is the onCancel method.
     * This is an event handler method that is called when the user clicks the cancel button.
     * The appointment will not be added and the application returns to the appointments screen.
     * @param actionEvent The event triggered when the cancel button is clicked
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    /**This is the onCountrySelected method.
     * This is an event handler method that is called when a user selects a country.
     * It fetches divisions based on the selected country and populates the statebox
     * with the corresponding divisions.
     * @param actionEvent The event triggered by selecting the country.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public void onCountrySelected(ActionEvent actionEvent) throws SQLException {
        // Get the selected country
        Country selectedCountry = countryBox.getValue();
        //Check if a Country is selected
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

    /**This is the onDivisionSelected method.
     * This is an event handler method that is called when the user selects a division.
     * It gets the selected division and fetches the corresponding division ID and stores it
     * in the divisionIDS field.
     * @param actionEvent The event triggered by selecting a division.*/
    public void onDivisionSelected(ActionEvent actionEvent) {
        //Get the selected Divisions
        String selectedDivision = (String) stateBox.getValue();
        //Check if a division is selected
        if (selectedDivision != null) {
            try {
                //fetch the division id based on the selected division's name
                divisionIDS = CustomerDAO.getDivisionIdByName(selectedDivision);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
