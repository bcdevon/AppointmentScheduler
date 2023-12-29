package Controller;

import Model.Country;
import Model.LocationReport;
import Model.Report;
import helper.CountryStringConverter;
import helper.MonthConverter;
import DAO.AppointmentDAO;
import DAO.AppointmentQuery;
import DAO.CustomerDAO;
import Model.Appointment;
import helper.MonthConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**This is the report_Controller class.
 * This class handles generating and displaying various reports.
 * It manages the interaction between the UI components and the data access objects.
 * It initializes the tableviews columns and combo boxes */
public class report_Controller implements Initializable {
    public TableView monthlyReportTable;
    public TableColumn monthCol;
    public TableColumn scheduleTypeCol;
    public TableColumn countCol;
    public TableView contactScheduleReport;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public ComboBox reportMonth;
    public ComboBox reportContact;
    public Button backButton;
    public ComboBox<Country> reportLocationBox;
    public TableView locationReportTable;
    public TableColumn locationCol;
    public TableColumn divisionCol;
    public TableColumn customerCol;

    /**This is the initialize method.
     * This method is called during initialization and sets up combo boxes, cell value factories and initial data.
     * @param url The location of the Report_Screen.fxml.
     * @param resourceBundle resources used for initialization.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set up cellValueFactory for each column
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        reportMonth.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        try {
            reportContact.setItems(CustomerDAO.getAllContactNames());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<Country> countries = CustomerDAO.getAllCountries();
            ObservableList<Country> countryList = FXCollections.observableList(countries);
            System.out.println(countryList);
            reportLocationBox.setItems(countryList);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Populates the Location Report table with data based on the selected country.
     * This method fetches divisions and their respective customer counts for the selected country
     * from the database and updates the locationReportTable accordingly. It also configures
     * the necessary TableColumn properties for the country, division, and customer count.
     */

    /**This is the populateLocationReport method.
     * This method populates the location report with data based on the selected country.
     * This method gets divisions and their customer counts for the selected country and displays
     * them in the table.*/
    private void populateLocationReport() {
        // Clear existing data
        locationReportTable.getItems().clear();

        //get the selected country from the combo box
        Country selectedCountry = reportLocationBox.getValue();

        if (selectedCountry != null) {
            try {
                List<String> divisions = CustomerDAO.getDivisionsByCountry(selectedCountry);
                //iterate through divisions and populate the table with customer count
                for (String division : divisions) {
                    try {
                        //get the divisions for the selected country
                        int divisionId = CustomerDAO.getDivisionIdByName(division);
                        //get the customer count for the divisions
                        long customerCount = CustomerDAO.getCustomerCountByDivision(divisionId);
                        //Add a new report entry to the table if there are customers.
                        if (customerCount > 0){
                            locationReportTable.getItems().add(new LocationReport(selectedCountry.getName(), division, customerCount));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Set up columns
                locationCol.setCellValueFactory(new PropertyValueFactory<>("country"));
                divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
                customerCol.setCellValueFactory(new PropertyValueFactory<>("customerCount"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**This is the populate monthly report method.
     * This method retrieves appointments for the selected month,
     * counts appointments by type, and displays results in the table.
     * The use of a lambda expression here captures the intent of the code for each appointment,
     * The typeCountMap. This clearly conveys the purpose of the loop and makes the code more maintainable.
     * Count occurrences of each type using Map.forEach this is one of the uses of a lambda expression*/
    private void populateMonthlyReport() {
        //Get selected month from combo box
        String selectedMonth = (String) reportMonth.getValue();
        //convert month name to its corresponding integer value
        int selectedMonthInt = MonthConverter.getMonthAsInt(selectedMonth);

        try {
            //get appointments for the selected month
            ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentsByMonthStart(selectedMonthInt);

            // Set up columns and add data to the table
            TableColumn<Report, String> monthCol = new TableColumn<>("Month");
            monthCol.setCellValueFactory(cellData -> new SimpleStringProperty(selectedMonth));

            TableColumn<Report, String> typeCol = new TableColumn<>("Type");
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Report, String> countCol = new TableColumn<>("Count");
            countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

            // Add columns to the table
            monthlyReportTable.getColumns().setAll(monthCol, typeCol, countCol);

            // Create a new list for displaying in the TableView
            ObservableList<Report> resultReport = FXCollections.observableArrayList();

            //The use of a lambda expression here captures the intent of the code for each appointment,
            // and updates the typeCountMap. This clearly conveys the purpose of the loop and makes the code more maintainable.
            // Count occurrences of each type using Map.forEach
            Map<String, Long> typeCountMap = new HashMap<>();
            //iterate over each appointment and update the typeCountMap.
            appointments.forEach(appointment -> {
                String type = appointment.getType();
                typeCountMap.put(type, typeCountMap.getOrDefault(type, 0L) + 1);
            });

            // Add each type with its count to the result list using Map.forEach
            typeCountMap.forEach((type, customerCount) -> resultReport.add(new Report(selectedMonth, type, customerCount)));

            // Add the appointments to the table
            monthlyReportTable.setItems(resultReport);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**Lambda Expression here, This is the populateContactScheduleReport method.
     * This method gets the appointments for the chosen contact.
     * It then populates the contact schedule report table based on the selected contact name.
     * Get appointments for the contact using a lambda expression
     * The lambda expression is concise, showing the logic for each Appointment.
     * It eliminates the need for explicit iteration using traditional loops.*/
    private void populateContactScheduleReport() {
        // Clear existing items
        contactScheduleReport.getItems().clear();

        // Get the selected contact name
        String selectedContactName = (String) reportContact.getValue();
        // Check if a contact is selected
        if (selectedContactName != null) {
            try {
                // Get contact ID based on the selected name
                int contactID = AppointmentQuery.getContactIDByName(selectedContactName);

                /*The lambda expression is concise, showing the logic for each Appointment.
                 It eliminates the need for explicit iteration using traditional loops.
                 The lambda expression specifies to add each appointment to the contactScheduleReport.*/
                //Retrieve the appointments for a specific contact by contact ID
                AppointmentDAO.getAppointmentsByContactID(contactID).forEach(appointment ->
                        //For each appointment add it to the contact schedule table
                        contactScheduleReport.getItems().add(appointment));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**This is the onNameSelected method.
     * This is an event handler method that is called when contact name is selected.
     * It calls the populateContactScheduleReport button to update the contact Schedule report table.
     * @param actionEvent The action event triggered by the button click.*/
    public void onNameSelected(ActionEvent actionEvent) {
        populateContactScheduleReport();
    }

    /**This is the onBackButton method.
     * This is and event handler method theat is called when the back button is clicked.
     * It navigates to the Appointments screen.
     * @param actionEvent The action event triggered when the back button is clicked.*/
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    /**This is the onMonthSelected method.
     * This is an event handler method that is called when a month is selected.
     * It calls the populateMonthlReport method to update the monthly report.
     * @param actionEvent The event triggered when a month is selected.*/
    public void onMonthSelected(ActionEvent actionEvent) {
        populateMonthlyReport();

    }

    /**This is the onLocationSelected method.
     * This method is called when a location country is selected.
     * It calls the populateLocationReport method to update the location report.
     * @param actionEvent The event triggered when a country is selected.*/
    public void onLocationSelected(ActionEvent actionEvent) {
      populateLocationReport();
    }
}

