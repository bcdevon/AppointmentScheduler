package DAO;
import Model.Country;
import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * From customers");
            String sql = "SELECT * From customers";
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("Customer_ID"));
                customer.setName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostal(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    /**get all countries method
     * database query to get all the countries
     * @return all the countries*/
    public static List<Country> getAllCountries() throws SQLException{
        List<Country> allCountries = new ArrayList<>();
        String sql = "SELECT Country_ID, Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            allCountries.add(new Country(countryID, countryName));
        }
        return allCountries;
    }

    /**get all divisions method
     * database query to get all divisions
     * @return all the divisions*/
    public static ObservableList<String> getAllDivisions() throws SQLException{
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            allDivisions.add(resultSet.getString("Division"));
        }
        return allDivisions;
    }

    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        try {
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                allCustomerIDs.add(customerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomerIDs;
    }

    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            while (resultSet.next()){
                String contactName = resultSet.getString("Contact_Name");
                allContactNames.add(contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContactNames;
    }

    public static ObservableList<Integer> getAppointmentsByContact(String contactName) throws SQLException {
        ObservableList<Integer> allContactAppointments = FXCollections.observableArrayList();

        // Step 1: Find contact ID by name
        String contactIdSql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        int contactId = -1;

        try (PreparedStatement contactIdPs = JDBC.connection.prepareStatement(contactIdSql)) {
            contactIdPs.setString(1, contactName);

            try (ResultSet contactIdResultSet = contactIdPs.executeQuery()) {
                if (contactIdResultSet.next()) {
                    contactId = contactIdResultSet.getInt("Contact_ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (contactId == -1) {
            // Contact not found
            return allContactAppointments;
        }

        // Step 2: Find appointments by contact ID
        String appointmentsSql = "SELECT Appointment_ID FROM appointments WHERE Contact_ID = ?";

        try (PreparedStatement appointmentsPs = JDBC.connection.prepareStatement(appointmentsSql)) {
            appointmentsPs.setInt(1, contactId);

            try (ResultSet appointmentsResultSet = appointmentsPs.executeQuery()) {
                while (appointmentsResultSet.next()) {
                    int appointmentID = appointmentsResultSet.getInt("Appointment_ID");
                    allContactAppointments.add(appointmentID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allContactAppointments;
    }


    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
        String sql = "SELECT User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        try{
            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                allUserIDs.add(userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserIDs;
    }



    //left off here create function to add a customer to the myql database
//    public static String addCustomer();

}
