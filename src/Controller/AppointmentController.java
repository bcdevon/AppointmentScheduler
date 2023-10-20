package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.CustomerQuery;
import Model.Appointment;
import Model.Customer;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    public ToggleGroup appointmentFilter;
    public TableView AppointmentTable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up cellValueFactory for each column
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
        populateAppointmentTable();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        populateCustomerTable();

    }
    private void populateAppointmentTable() {
        // Create an instance of your AppointmentDAO
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        // Fetch the appointment data from the database
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        // Create an ObservableList to store the data and set it to the TableView
        ObservableList<Appointment> appointmentData = FXCollections.observableArrayList(appointments);
        // Set the appointment data to the TableView
        AppointmentTable.setItems(appointmentData);
    }

    private void populateCustomerTable(){
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers = customerDAO.getAllCustomers();
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customers);
        CustomerTable.setItems(customerData);
    }

    public void onADDAppointment(ActionEvent actionEvent) throws IOException {
        //load Add Appointment screen
        Parent add_Appointment_parent = FXMLLoader.load(getClass().getResource("../View/add_Appointment.fxml"));
        Scene add_Appointment_scene = new Scene(add_Appointment_parent);
        
        //get the current window and set the scene to the add appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(add_Appointment_scene);
        stage.show();
    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        //load Update Appointment screen
        Parent update_Appointment_parent = FXMLLoader.load(getClass().getResource("../View/update_Appointments.fxml"));
        Scene update_Appointment_scene = new Scene(update_Appointment_parent);

        //get the current window and set the scene to the Update appointment scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(update_Appointment_scene);
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        //Load Add Customer Screen
        Parent add_Customer_parent = FXMLLoader.load(getClass().getResource("../View/add_Customer.fxml"));
        Scene add_Customer_scene = new Scene(add_Customer_parent);

        //Get the current window and set the scene to the add customer scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(add_Customer_scene);
        stage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        //load Update Customer screen
        Parent update_Customer_parent = FXMLLoader.load(getClass().getResource("../View/update_Customer.fxml"));
        Scene update_Customer_scene = new Scene(update_Customer_parent);

        //get the current window and set the scene to the Update Customer scene
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(update_Customer_scene);
        stage.show();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        //get selected customer from the CustomerTable;
        Customer selectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();

        //If no part is selected, return and do nothing
        if (selectedCustomer == null){
            return;
        }
        //Display a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Do you want to delete this Customer");

        //Wait for response and check if they clicked ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            //Delete Customer if ok is clicked
            int deletedCustomerID = selectedCustomer.getId();
            int rowsAffected = CustomerQuery.delete(deletedCustomerID);
            if(rowsAffected > 0){
                System.out.println("Customer deleted");
            }
        }

    }

}
