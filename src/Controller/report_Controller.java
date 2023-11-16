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
    private void populateLocationReport() {
        locationReportTable.getItems().clear(); // Clear existing data

        Country selectedCountry = reportLocationBox.getValue();
        if (selectedCountry != null) {
            try {
                List<String> divisions = CustomerDAO.getDivisionsbyCountry(selectedCountry);

                System.out.println("Selected Country: " + selectedCountry.getName());

                locationReportTable.getItems().add(new LocationReport(selectedCountry.getName(), "", 0));

                for (String division : divisions) {
                    try {
                        long customerCount = CustomerDAO.getCustomerCountByDivision(selectedCountry, division);
                        locationReportTable.getItems().add(new LocationReport(selectedCountry.getName(), division, customerCount));
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



//    private void populateLocationReport() {
//        Country selectedCountry = reportLocationBox.getValue();
//        if (selectedCountry != null) {
//            try {
//                // Assuming you have methods in CustomerDAO to get divisions by country
//                List<String> divisions = CustomerDAO.getDivisionsbyCountry(selectedCountry);
//                System.out.println("Selected Country: " + selectedCountry.getName());
//
//                // Display the selected country in locationCol
//                locationCol.setCellValueFactory(cellData -> new SimpleStringProperty(selectedCountry.getName()));
//                divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));  // Adjust this based on your Report class
//                customerCol.setCellValueFactory(new PropertyValueFactory<>("customerCount"));  // Adjust this based on your Report class
//
//
//                // Display divisions and customer counts
//                for (String division : divisions) {
//                    long customerCount = CustomerDAO.getCustomerCountByDivision(division);
//                    System.out.println("Division: " + division + ", Customer Count: " + customerCount);
//                    locationReportTable.getItems().add(new Report(selectedCountry.getName(), division, customerCount));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void populateMonthlyReport() {
        // Get selected month from combo box
        String selectedMonth = (String) reportMonth.getValue();
        // Convert the month name to its corresponding integer value
        int selectedMonthInt = MonthConverter.getMonthAsInt(selectedMonth);

        try {
            // Get appointments for the selected month
            ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentsByMonthStart(selectedMonthInt);

            // Map to store count of each type
            Map<String, Long> typeCountMap = new HashMap<>();

            // Count occurrences of each type
            for (Appointment appointment : appointments) {
                String type = appointment.getType();
                typeCountMap.put(type, typeCountMap.getOrDefault(type, 0L) + 1);
            }

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

            // Add each type with its count to the result list
            typeCountMap.forEach((type, customerCount) -> resultReport.add(new Report(selectedMonth, type, customerCount)));

            // Add the fetched appointments to the table
            monthlyReportTable.setItems(resultReport);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    private void populateMonthlyReport() {
//        // Get selected month from combo box
//        String selectedMonth = (String) reportMonth.getValue();
//
//        // Convert the month name to its corresponding integer value
//        int selectedMonthInt = MonthConverter.getMonthAsInt(selectedMonth);
//
//        try {
//            // Get appointments for the selected month
//            ObservableList<Appointment> appointments = AppointmentDAO.getAppointmentsByMonthStart(selectedMonthInt);
//
//            // Set up columns and add data to the table
//            TableColumn<Appointment, String> monthCol = new TableColumn<>("Month");
//            // Set the value for monthCol
//            monthCol.setCellValueFactory(cellData -> new SimpleStringProperty(selectedMonth));
//
//            TableColumn<Appointment, String> typeCol = new TableColumn<>("Type");
//            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
//
//            TableColumn<Appointment, String> countCol = new TableColumn<>("Appointment ID");
//            countCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//            // Add columns to the table
//            monthlyReportTable.getColumns().setAll(monthCol, typeCol, countCol);
//
//            // Add the fetched appointments to the table
//            monthlyReportTable.setItems(appointments);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }


    private void populateContactScheduleReport() {
        // Clear existing items
//        contactScheduleReport.getItems().clear();

        // Get the selected contact name
        String selectedContactName = (String) reportContact.getValue();
        // Check if a contact is selected
        if (selectedContactName != null) {
            try {
                // Get contact ID based on the selected name
                int contactID = AppointmentQuery.getContactIDByName(selectedContactName);
                // Fetch appointments for the contact
                ObservableList<Appointment> contactAppointments = AppointmentDAO.getAppointmentsByContactID(contactID);
                // Populate the table with the fetched appointments
                contactScheduleReport.setItems(contactAppointments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNameSelected(ActionEvent actionEvent) {
        populateContactScheduleReport();
    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent appointment_parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene appointment_scene = new Scene(appointment_parent);

        //Get the current window and set the scene to the appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(appointment_scene);
        stage.show();
    }

    public void onMonthSelected(ActionEvent actionEvent) {
        populateMonthlyReport();

    }

    public void onLocationSelected(ActionEvent actionEvent) {
        //get the selected country
      populateLocationReport();

    }
}

