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

/**This is the update_CustomerController class.
 * This class manages modifying and saving changes to customer information. It updates
 * the customer information in the database and handles input validation.*/
public class update_CustomerController implements Initializable {

    /**Text field for updating the customer ID. */
    public TextField updateIDTF;

    /**Text field for updating the customer name. */
    public TextField updateNameTF;

    /**Text field for updating the customer address. */
    public TextField updateAddressTF;

    /**Text field for updating the customer postal code. */
    public TextField updatePostalCodeTF;

    /**Text field for updating the customer phone number. */
    public TextField updatePhoneTF;

    /**Combo box to update the Country associated with the customer. */
    public ComboBox <Country> updateCountryComboBox;

    /**Combo box to update the division associated with the customer. */
    public ComboBox updateDivisionComboBox;

    /**Private variable to store the Division ID. */
    private int divisionIDS;

    /**This is the initialize method.
     * This method is called during initialization and sets up the Country and Division Combo Boxes.
     * @param url The location of the update_Customer.fxml.
     * @param resourceBundle resources used for initialization.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Populate the Country Combo Box
            List<Country> countries = CustomerDAO.getAllCountries();
            updateCountryComboBox.setItems(FXCollections.observableList(countries));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            //Populate the Division Combo Box
            List<String> divisions = CustomerDAO.getAllDivisions();
            updateDivisionComboBox.setItems(FXCollections.observableList(divisions));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //initial focus on the screen is the updateNameTF
        Platform.runLater(() -> updateNameTF.requestFocus());
    }

    /**This is the onSave method.
     * This is an event handler method that is called when the user clicks the save button.
     * It saves the updated customer information to the database and navigates back to the appointments screen.
     * @param actionEvent The event triggered when the save button is clicked.*/
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        //get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        //get the data from the update customer fields
        int customerIDS = Integer.parseInt(updateIDTF.getText());
        String nameS = updateNameTF.getText();
        String addressS = updateAddressTF.getText();
        String postalS = updatePostalCodeTF.getText();
        String phoneS = updatePhoneTF.getText();
        String createdateS = currentDateTime.format(formatter);
        String createdbyS = CurrentUser.getCurrentUser().getUsername();
        String lastUpdatedS = formattedDateTime;
        String lastUpdateByS = CurrentUser.getCurrentUser().getUsername();

        //update the customer information in the database
        int rowsAffected = CustomerQuery.update(customerIDS, nameS, addressS, postalS, phoneS, createdateS, createdbyS, lastUpdatedS, lastUpdateByS, divisionIDS);

        //navigate backt to appointments screen
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    /**This is the onCancel method.
     * This is an event handler method that is called when the cancel button is clicked.
     * It navigates to the appointments screen and does not save any updates.
     * @param actionEvent The event triggered when the cancel button is clicked.*/
    public void onCancel(ActionEvent actionEvent) throws IOException {
        //Navigate to appointment screen
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    /**This is the onupdateCountrySelected method.
     * This is an event handler method that is called when a country is selected in the update screen.
     * It populates the division combo Box based on the country that is selected.
     * @param actionEvent The event triggered when a country is selected.*/
    public void onupdateCountrySelected(ActionEvent actionEvent) {
        Country selectedCountry = updateCountryComboBox.getValue();

        if (selectedCountry != null) {
            try {
                // Fetch divisions based on the selected country
                ObservableList<String> divisions = FXCollections.observableList(CustomerDAO.getDivisionsByCountry(selectedCountry));
                // Set divisions to the division combo box
                updateDivisionComboBox.setItems(divisions);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    /**This is the onUpdateDivisionSelected method.
     * This is an event handler method that is called when a division is selected.
     * If a division is selected this method will retrieve the division ID.*/
    public void onUpdateDivisionSelected(ActionEvent actionEvent) {
        //get the selected division.
        String selectedDivision = (String) updateDivisionComboBox.getValue();

        //if a division is selected get the division ID based on the name
        if (selectedDivision != null) {
            try {
                divisionIDS = CustomerDAO.getDivisionIdByName(selectedDivision);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
