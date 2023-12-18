package DAO;
import java.time.LocalDateTime;

import Model.Appointment;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**This is the AppointmentDAO class.
 * It provides method to interact with the database for managing appointments.
 * Including functions to retrieve all appointments, filter appointments by month or week,
 * get appointments by contact,month start, and customer ID.*/
public class AppointmentDAO {

    /**This is the getAllAppointments method.
     * This method gets all appointments from the database.
     * @return List of all appointment in the database*/
    public List<Appointment> getAllAppointments() {
        //Initialize a list to store appointments
        List<Appointment> appointments = new ArrayList<>();
        try {
            //SQL statement to select all columns from the appointment table
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * FROM appointments");
            String sql = "SELECT * FROM appointments";
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            // Iterate through the result set and create Appointment objects
            while (resultSet.next()) {
                //extract appointment details from the result set
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                //add the created appointment object to the list
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**This is the extractAppointmentFromResultSet method.
     * This method extract appointment details from a ResultSet and creates an appointment object.
     * @param resultSet The resultset containting the appointment data.
     * @return Appointment object created for resultset data.*/
    private static Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        //Extract individual fields from the resultset
        int id = resultSet.getInt("Appointment_ID");
        String title = resultSet.getString("Title");
        String description = resultSet.getString("Description");
        String location = resultSet.getString("Location");
        int contactID = resultSet.getInt("Contact_ID");
        String type = resultSet.getString("Type");
        LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
        LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
        int userID = resultSet.getInt("User_ID");
        int customerID = resultSet.getInt("Customer_ID");
        //create and return an appointment object
        return new Appointment(id, title, description, location, type, start, end, customerID, userID, contactID);
    }

    /**This is the getAppointmentsByContactID method.
     * This method retrieves a list of appointments associated with a specific contact ID.
     * @param contactID The contact ID of the appointments we are retrieving
     * @return List of appointment objects for the specified contact.*/
    public static ObservableList<Appointment> getAppointmentsByContactID(int contactID) {
        //Initialize an observablelist to store appointments
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        //SQL statement to select appointments for a specific contact ID
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, contactID);
            //execute sql query
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop thorugh result set and create appointments
            while (resultSet.next()) {
                //Extract appointment details from the result set
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                //add the created Appointment object to the list
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Return the observable list of Appointment objects
        return appointments;
    }

    /**This is the getAppointmentsByMonthStart method.
     * This method gets a list of appointments for a specified month.
     * @param month The month the appointments are retrieved for.
     * @return Observable list of Appointment objects for the selected month*/
    public static ObservableList<Appointment> getAppointmentsByMonthStart(int month) throws SQLException {
        //Initialize observable list to store Appointment objects
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        // Use a SQL query to get appointments for the specified month
        String sql = "SELECT * FROM appointments WHERE MONTH(start) = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, month);
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop throught the result set and create Appointment objects
            while (resultSet.next()) {
                //extract appointment details from the result set
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                //add the created Appointment objects to the list
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return the observable list Appointment objects
        return appointments;

    }

    /**This is the getAppointmentsByCustomerID method.
     * This method retrieves a list of appointments for a specific customer.
     * @param customerId The customer ID of the customer whos appointments we are getting.
     * @return Lis of Appointment objects for the selected customer.*/
    public static List<Appointment> getAppointmentsByCustomerId(int customerId) throws SQLException{
        //Initialize a list to store Appointment objects
        List<Appointment> appointments = new ArrayList<>();
        //SQL query to select appointments for a specific customer ID
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Loop thorugh the result set and create Appointment objects
            while (resultSet.next()) {
                //Extract appointment details from the result set
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                //Add the created Appointment object to the list
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Return the list of Appointment objects
        return appointments;
    }
}
