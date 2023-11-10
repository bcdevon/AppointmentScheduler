package Controller;

import DAO.AppointmentDAO;
import DAO.AppointmentQuery;
import DAO.CustomerDAO;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
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
    }


    private void populateContactScheduleReport() {
        // Clear existing items
//        contactScheduleReport.getItems().clear();

        // Get the selected contact name
        String selectedContactName = (String) reportContact.getValue();
        System.out.println("Selected Contact Name: " + selectedContactName);  // Add this line
        // Check if a contact is selected
        if (selectedContactName != null) {
            try {
                // Get contact ID based on the selected name
                int contactID = AppointmentQuery.getContactIDByName(selectedContactName);
                System.out.println("Selected Contact: " + selectedContactName);
                System.out.println("Contact ID for " + selectedContactName + ": " + contactID);
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
}

