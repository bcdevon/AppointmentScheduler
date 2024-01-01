package Controller;

import DAO.AppointmentDAO;
import DAO.AppointmentQuery;
import DAO.CustomerDAO;
import DAO.CustomerQuery;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import helper.AppointmentReminder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the AppointmentController Class.
 * This is the controller class for managing appointments in the application.
 * It handles interactions between the user interface and the data access objects.*/
public class AppointmentController implements Initializable {
    public ToggleGroup appointmentFilter;
    public TableView<Appointment> AppointmentTable;
    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIDCol;
    public TableColumn apptUserIDCol;
    public TableView CustomerTable;
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCodeCol;
    public TableColumn customerPhoneNumberCol;
    public RadioButton Month;
    public RadioButton Week;
    public RadioButton All;
    public Button reportButton;
    public Button deleteappointmentButton;
    public TableColumn DivisionNameCol;
    public TableColumn DivisionIDCol;
    //Default filter selection
    private String selectedFilter;

    /**This is the initialize method.
     * This method is called during initialization and sets up the Appointment table, and
     * Customer Table with customers and appointments from the database.
     * It also sets up the columns in the tables to display the correct information
     * @param resourceBundle resourced used for initialization
     * @param url The location of the Appointments.fxml*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Check for upcoming appointments
        AppointmentReminder.checkAppointments();


        // Set the values for the appointment table columns
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        //Populate the appointment table
        populateAppointmentTable();

        //Set the values for the customer table columns
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        DivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        DivisionNameCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        //Populate the customer table.
        populateCustomerTable();


    }

    /**This is the populateAppointmentTable method.
     * This method gets all the appointment data, creates an observable list
     * and sets it to the tableview.*/
    private void populateAppointmentTable() {
        // Create an instance of your AppointmentDAO
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        // Fetch the appointment data from the database
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        // Create an Observable List to store the data and set it to the TableView
        ObservableList<Appointment> appointmentData = FXCollections.observableArrayList(appointments);
        // Set the appointment data to the TableView
        AppointmentTable.setItems(appointmentData);

    }

    /**This is the populateCustomerTable.
     * This method get all the customer data, creates an observable list
     * and sets it to the tableview.*/
    private void populateCustomerTable(){
        // Create an instance of your CustomerDAO
        CustomerDAO customerDAO = new CustomerDAO();
        // Fetch the customer data from the database
        List<Customer> customers = customerDAO.getAllCustomers();
        // Create an Observable List to store the data and set it to the tableview
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customers);
        // Set the Customer data to the tableview
        CustomerTable.setItems(customerData);

    }

    /**This is the onADDAppointment method.
     * This is an event handler method that is called when the user clicks add appointment.
     * It navigates the user to the add appointment screen.
     * @param actionEvent The event triggered when the Add button is clicked.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onADDAppointment(ActionEvent actionEvent) throws IOException {
        //load Add Appointment screen
        Parent add_Appointment_parent = FXMLLoader.load(getClass().getResource("../View/add_Appointment.fxml"));
        Scene add_Appointment_scene = new Scene(add_Appointment_parent);
        
        //get the current window and set the scene to the add appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(add_Appointment_scene);
        stage.show();
    }

    /**This is the onUpdateAppointment method.
     * This is an event handler method that is called when the user clicks update appointment.
     * It fetches the selected appointment and also verifies that an appointment was selected.
     * It also sets the fields and combo Boxes with the values from the selected appontment and
     * navigates to the update appointment screen.
     * @param actionEvent The event triggered when the Update button is clicked.
     * @throws SQLException If a SQL exception occurs during the database operation.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        //get the selected appointment
        Appointment selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null){
            //load Update Appointment screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/update_Appointments.fxml"));
            Parent update_Appointment_parent = loader.load();

            // Access the controller of the update screen
            update_AppointmentsController updateController = loader.getController();

            String startDateTimeString = String.valueOf(selectedAppointment.getStart());
            String endDateTimeString = String.valueOf(selectedAppointment.getEnd());
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            LocalTime endTime = endDateTime.toLocalTime();
            LocalTime startTime = startDateTime.toLocalTime();
            String endTimeString = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            String startTimeString = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));

            updateController.updateStartTimeComboBox.setValue(startTimeString);
            updateController.updateEndTimeComboBox.setValue(endTimeString);


            // Prepopulate the fields in the update screen
            updateController.updateAppointmentIDTF.setText(String.valueOf(selectedAppointment.getId()));
            updateController.updateTitleTF.setText(selectedAppointment.getTitle());
            updateController.updateDescriptionTF.setText(selectedAppointment.getDescription());
            updateController.updateLocationTF.setText(selectedAppointment.getLocation());
            String contactName = AppointmentQuery.getContactNamebyID(selectedAppointment.getContact());
            updateController.updateContactComboBox.setValue(contactName);
            updateController.updateTypeTF.setText(selectedAppointment.getType());
            updateController.updateUserIDComboBox.setValue(selectedAppointment.getUserID());
            updateController.updateCustomerIDComboBox.setValue(selectedAppointment.getCustomerID());
            updateController.updateStartDate.setValue(selectedAppointment.getStart().toLocalDate());
            updateController.updateEndDate.setValue(selectedAppointment.getEnd().toLocalDate());

            // Set the scene
            Scene update_Appointment_scene = new Scene(update_Appointment_parent);

            //get the current window and set the scene to the Update appointment scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(update_Appointment_scene);
            stage.show();
        }
        else return;
    }

    /**This is the onDeleteAppointment method.
     * This is an event handler method that is called when the user clicks the delete appointment button.
     * It gets the selected appointment and verifies an appointment was selected.
     * It then deletes the appointment from the database based on appointment ID
     * displaying an alert before deleting to make sure nothing is deleted by accident.
     * @param actionEvent The event triggered when the delete button is clicked.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        //get selected appointment from the appointmentTable;
        Appointment selectedAppointment = (Appointment) AppointmentTable.getSelectionModel().getSelectedItem();

        //If no appointment is selected, return and do nothing
        if (selectedAppointment == null){
            return;
        }
        //Display a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Are you sure you want to delete Appointment ID: " + selectedAppointment.getId() + " Type: " + selectedAppointment.getType());

        //Wait for response and check if they clicked ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            //Delete Customer if ok is clicked
            int deletedAppointmentID = selectedAppointment.getId();
            int rowsAffected = AppointmentQuery.delete(deletedAppointmentID);
            if(rowsAffected > 0){
                //Appointment was deleted updated appointment table
                populateAppointmentTable();
                System.out.println("Appointment deleted");
            }
        }
    }

    /**This is the onAddCustomer method.
     * This is an event handler method that is called when the Add Customer button is clicked.
     * It navigates to the add Customer Screen.
     * @param actionEvent The event triggered when the Add customer button is clicked.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        //Load Add Customer Screen
        Parent add_Customer_parent = FXMLLoader.load(getClass().getResource("../View/add_Customer.fxml"));
        Scene add_Customer_scene = new Scene(add_Customer_parent);

        //Get the current window and set the scene to the add customer scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(add_Customer_scene);
        stage.show();
    }

    /**This is the onUpdateCustomer method.
     * This is an event handler method that is called when the user clicks the update customer button.
     * It gets the selected customer verifies a customer was selected and navigates to the update customer screen.
     * The fields and combo boxes in the update screen are pre-populated with the values from
     * the selected customer.
     * @param actionEvent The event triggered whe the update button is clicked.
     * @throws SQLException If a SQL exception occurs during the database operation.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        //Get the selected customer from the table
        Customer selectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        int divisionID = selectedCustomer.getDivisionId();
        int countryID = CustomerDAO.getCountryIDByDivisionID(divisionID);
        String countryName = CustomerDAO.getCountryByCountryID(countryID);
        Country country = new Country(countryID, countryName);
        System.out.println("Selected Customer Division ID: " + selectedCustomer.getDivisionId());

        //Check if a customer is selected
        if (selectedCustomer != null){
            //load Update Customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/update_Customer.fxml"));
            Parent update_Customer_parent = loader.load();

            //Access the controller of the update screen.
            update_CustomerController customerUpdate = loader.getController();

            //Prepopulate fields in the customer update screen.
            customerUpdate.updateIDTF.setText(String.valueOf(selectedCustomer.getId()));
            customerUpdate.updatePhoneTF.setText(selectedCustomer.getPhone());
            customerUpdate.updateAddressTF.setText(selectedCustomer.getAddress());
            customerUpdate.updatePostalCodeTF.setText(selectedCustomer.getPostal());
            customerUpdate.updateNameTF.setText(selectedCustomer.getName());
            customerUpdate.updateDivisionComboBox.setValue(selectedCustomer.getDivision());
            customerUpdate.updateCountryComboBox.setValue(country);

            //Set the scene
            Scene update_Customer_scene = new Scene(update_Customer_parent);
            //get the current window and set the scene to the Update Customer scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(update_Customer_scene);
            stage.show();
        }
    }

    /**This is the onDeleteCustomer method.
     * This is an event handler method that is called when the delete customer button is clicked.
     * It gets the selected customer, verifies a customer is selected and checks if the customer
     * has any appointments. If the customer has any appointments it displays a warning message
     * and the user must confirm they want to delete the customer and all associated appointments.
     * If the customer has no appointments and the user confirms the
     * deletion the customer is deleted.
     * @param actionEvent The event triggered when the delete button is clicked.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        // Get selected customer from the CustomerTable;
        Customer selectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        // Print selected customer information
        System.out.println("Selected Customer: " + selectedCustomer);
        // If no customer is selected, return and do nothing
        if (selectedCustomer == null) {
            System.out.println("No customer selected. Exiting.");
            return;
        }
        // Check if the customer has appointments
        if (hasAppointments(selectedCustomer.getId())) {
            // Display a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Customer and Appointments");
            alert.setContentText("This customer has appointments. Do you want to delete the customer and associated appointments?");
            // Wait for response and check if they clicked OK
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("User confirmed deletion.");
                // Delete Appointments first
                deleteAppointments(selectedCustomer.getId());
                // Then delete the Customer
                int deletedCustomerID = selectedCustomer.getId();
                int rowsAffected = CustomerQuery.delete(deletedCustomerID);
                // Print the number of rows affected
                System.out.println("Rows affected after deletion: " + rowsAffected);
                if (rowsAffected > 0) {
                    // Customer and Appointments were deleted, update customer table
                    populateCustomerTable();
                    System.out.println("Customer and associated Appointments deleted");
                }
            }
        } else {
            // No appointments, proceed with deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Customer");
            alert.setContentText("Do you want to delete this Customer");
            // Wait for response and check if they clicked OK
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("User confirmed deletion.");
                // Delete Customer
                int deletedCustomerID = selectedCustomer.getId();
                int rowsAffected = CustomerQuery.delete(deletedCustomerID);
                // Print the number of rows affected
                System.out.println("Rows affected after deletion: " + rowsAffected);
                if (rowsAffected > 0) {
                    // Customer was deleted, update customer table
                    populateCustomerTable();
                    System.out.println("Customer deleted");
                }
            }
        }
    }

    /**This is the deleteAppointments method.
     * This method deletes all appointments associated with the customer.
     * @param customerId The ID of the customer whose appointments need to be deleted.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    private void deleteAppointments(int customerId) throws SQLException {
        // Delete all appointments associated with the customer
        int rowsAffected = AppointmentQuery.deleteAppointmentsByCustomer(customerId);
        System.out.println("Rows affected after deleting appointments: " + rowsAffected);
        //update the appointment table to display the appointment deletions.
        populateAppointmentTable();
    }

    /**This is the hadAppointments method.
     * This method gets appointments from the database based on the cusotmer ID
     * and checks if there are any appointments for that customer.
     * @param customerId The ID of the customer to check for appointments.
     * @return True if the customer has appointments else False.*/
    private boolean hasAppointments(int customerId) throws SQLException {
        // Fetch appointments for the given customer ID
        List<Appointment> appointments = AppointmentDAO.getAppointmentsByCustomerId(customerId);

        // Check if there are any appointments
        return !appointments.isEmpty();
    }

    /**This is the onMonthSelected method.
     * This is an event handler method that is called when the user selects the month filter.
     * When the month filter is selected the displayed appointments are updated to show the current months appointments.
     * @param actionEvent The event triggered when the month filter is selected.*/
    public void onMonthSelected(ActionEvent actionEvent) {
        selectedFilter = "Month";
        //Check if month filter was selected
        if(selectedFilter.equals("Month")){
            // Filter appointments for the current month
            List<Appointment> filteredAppointments = filteredAppointmentsbyMonth();
            displayAppointments(filteredAppointments);
        }
    }

    /**This is the onWeekSelected method.
     * This is an event handler method that is called when the user selects the week filter.
     * When the week filter is selected the displayed appointments are updated to show the current weeks appointments.
     * @param actionEvent The event triggered when the week filter is selected.*/
    public void onWeekSelected(ActionEvent actionEvent) {
        selectedFilter = "Week";
        //Check if week filter was selected
        if (selectedFilter.equals("Week")) {
            // Filter appointments for the current week
            List<Appointment> filteredAppointments = filterAppointmentsByWeek();
            displayAppointments(filteredAppointments);
        }
    }

    /**This is the filteredAppointmentsbyMonth method.
     * This method gets the current date and filters appointments that fall within the current month
     * and returns a list of filtered appointments.
     * @return List of appointments filtered by the current month.*/
    private List<Appointment> filteredAppointmentsbyMonth() {
        //Populate appointment table with current data.
        populateAppointmentTable();
        //get current date
        LocalDate currentDate = LocalDate.now();
        //filter appointments for current month
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : AppointmentTable.getItems()) {
            LocalDate appointmentDate = appointment.getStart().toLocalDate();
            if (appointment.getStart().getYear() == currentDate.getYear() &&
            appointment.getStart().getMonth() == currentDate.getMonth()) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    /**This is the filterAppointmentsByWeek method.
     * This method gets the current date calculates the start and end of a week
     * and filters the appointments that fall within the current week.
     * It then returns a list of filtered appointments by week.
     * @return List of filtered appointments by week.*/
    private List<Appointment> filterAppointmentsByWeek() {
        //Populate appointment table with current data
        populateAppointmentTable();
        //get current date
        LocalDateTime currentDate = LocalDateTime.now();
        //calculate start of week
        LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        //calculate end of week
        LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY).plusHours(59).plusSeconds(59);
        // Filter appointments for the current week
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : AppointmentTable.getItems()) {
            if (appointment.getStart().isAfter(startOfWeek) && appointment.getStart().isBefore(endOfWeek)) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    /**This is the displayAppointments method.
     * This method takes a list of filtered appointments and updates the table view to display them.
     * @param filteredAppointments the list of appointments to display*/
    private void displayAppointments(List<Appointment> filteredAppointments) {
        //Create an ObsevableList for the filtered appointments
        ObservableList<Appointment> filteredAppointmentData = FXCollections.observableArrayList(filteredAppointments);
        //Set the ObservableList as the datasource for the appointment table.
        AppointmentTable.setItems(filteredAppointmentData);
    }

    /**This is the onAllSelected method.
     * This is an event handler method that is called when the all filter is selected.
     * It populates the appointment table to show all the appointments.
     * @param actionEvent The event triggered when the all filter is selected*/
    public void onAllSelected(ActionEvent actionEvent) {
        populateAppointmentTable();
    }

    /**This is the onReportButton method.
     * This is an event handler method that is called when the report button is clicked.
     * This method navigates to the report screen when the report button is clicked.
     * @param actionEvent The event triggered when the report button is clicked.
     * @throws IOException  If an I/O exception occurs during the method execution.*/
    public void onReportButton(ActionEvent actionEvent) throws IOException {
        //load Update report screen
        Parent report_Screen_parent = FXMLLoader.load(getClass().getResource("../View/Report_Screen.fxml"));
        Scene report_Screen_scene = new Scene(report_Screen_parent);

        //get the current window and set the scene to the Report Screen scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(report_Screen_scene);
        stage.show();
    }
}

