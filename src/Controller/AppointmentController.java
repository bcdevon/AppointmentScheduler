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
     * @param actionEvent The event triggered when the Add button is clicked.*/
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
     * @
     * */
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
     * @param actionEvent The event triggered when the delete button is clicked.*/
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
        alert.setContentText("Do you want to delete this Appointment");

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
     * @param actionEvent The event triggered when the Add customer button is clicked.*/
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
     * @param actionEvent The event triggered whe the update button is clicked.*/
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
     * and does not delete the customer. If the customer has no appointments and the user confirms the
     * deletion the customer is deleted.
     * @param actionEvent The event triggered when the delete button is clicked.*/
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        //get selected customer from the CustomerTable;
        Customer selectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();

        // Print selected customer information
        System.out.println("Selected Customer: " + selectedCustomer);

        //If no part is selected, return and do nothing
        if (selectedCustomer == null){
            System.out.println("No customer selected. Exiting.");
            return;
        }

        // Check if the customer has appointments
        if (hasAppointments(selectedCustomer.getId())) {
            // Display a message indicating that the customer has appointments
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Customer has Appointments");
            alert.setContentText("This customer has appointments. Please delete the appointments before deleting the customer.");
            alert.showAndWait();
            return; // Do not proceed with deletion
        }

        // Print information about the selected customer
        System.out.println("Selected Customer ID: " + selectedCustomer.getId());
        //Display a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Do you want to delete this Customer");

        //Wait for response and check if they clicked ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("User confirmed deletion.");
            //Delete Customer if ok is clicked
            int deletedCustomerID = selectedCustomer.getId();
            int rowsAffected = CustomerQuery.delete(deletedCustomerID);
            // Print the number of rows affected
            System.out.println("Rows affected after deletion: " + rowsAffected);

            if(rowsAffected > 0){
                //Customer was deleted updated customer table
                populateCustomerTable();
                System.out.println("Customer deleted");
            }
        }






    }
    // Helper method to check if a customer has appointments
    private boolean hasAppointments(int customerId) throws SQLException {
        // Fetch appointments for the given customer ID
        List<Appointment> appointments = AppointmentDAO.getAppointmentsByCustomerId(customerId);

        // Check if there are any appointments
        return !appointments.isEmpty();
    }

    public void onMonthSelected(ActionEvent actionEvent) {
        selectedFilter = "Month";
        if(selectedFilter.equals("Month")){
            List<Appointment> filteredAppointments = filteredAppointmentsbyMonth();
            displayAppointments(filteredAppointments);
        }
    }

    public void onWeekSelected(ActionEvent actionEvent) {
        selectedFilter = "Week";
        if (selectedFilter.equals("Week")) {
            // Filter appointments for the current week
            List<Appointment> filteredAppointments = filterAppointmentsByWeek();
            displayAppointments(filteredAppointments);
        }
    }
    //helper method to filter appointments by month
    private List<Appointment> filteredAppointmentsbyMonth() {
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

    private List<Appointment> filterAppointmentsByWeek() {
        populateAppointmentTable();
        LocalDateTime currentDate = LocalDateTime.now();
        //calculate start of week
        LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY);
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
    // Helper method to display the filtered appointments in the table
    private void displayAppointments(List<Appointment> filteredAppointments) {
        ObservableList<Appointment> filteredAppointmentData = FXCollections.observableArrayList(filteredAppointments);
        AppointmentTable.setItems(filteredAppointmentData);
    }

    public void onAllSelected(ActionEvent actionEvent) {
        populateAppointmentTable();
    }

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

