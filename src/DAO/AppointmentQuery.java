package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * The AppointmentQuery class provides methods for performing database operations related to appointments.
 * It includes methods for selecting, inserting, updating, and deleting appointment records.
 */

/**This is the AppointmentQuery class.
 * This class provides methods for performing database operations related to appointments.
 * It includes methods for selecting, inserting, updating, and deleting appointment records. */
public class AppointmentQuery {

    /**This is the insert method.
     * This method insert a new appointment into the appointments table in the database.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param createDate The date and time the appointment was created.
     * @param createdBy The user who created the appointment.
     * @param lastUpdated  The date and time the appointment was last updated.
     * @param lastUpdatedby  The user who last updated the appointment.
     * @param customerID  The ID of the customer associated with the appointment.
     * @param userID The ID of the user associated with the appointment.
     * @param contact The ID of the contact associated with the appointment.
     * @return The number of rows affected by the insertion 1 if successful 0 if not.*/
    public static int insert(String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int userID, int customerID, int contact) throws SQLException {
        //Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //SQL query to insert new appointment record in the database
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        // set the SQL query parameters
        ps.setString(1, title );
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, createDate);
        ps.setString(8, createdBy);
        ps.setString(9, lastUpdated);
        ps.setString(10, lastUpdatedby);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contact);
        //Execute the SQL query
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This is the update method.
     * This method update an existing appointment record in the appointments table base on the provided appointment ID.
     * @param appointmentId The ID of the appointment to be updated.
     * @param title The updated title of the appointment.
     * @param description The updated description of the appointment.
     * @param location The updated location of the appointments.
     * @param type The updated Type of appointment.
     * @param start The updated start date and time of the appointment.
     * @param end The updated end date and time of the appointment.
     * @param createDate The date and time the appointment was created this will stay the same.
     * @param createdBy The user the appointment was created by this will stay the same.
     * @param lastUpdated The date and time the appointment was last updated.
     * @param lastUpdatedby The user the appointment was last update by.
     * @param contact The ID of the contact associated with the appointment.
     * @param userID The ID of the user associated with the appointment.
     * @param customerID The ID of the customer associated with the appointment.
     * @return The number of rows affected by the update 1 if successful 0 if not.*/
    public static int update(int appointmentId, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int userID, int customerID, int contact) throws SQLException {
        //format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //SQL query to update an existing appointment.
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Set the parameters for the SQL query.
        ps.setString(1, title );
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, createDate);
        ps.setString(8, createdBy);
        ps.setString(9, lastUpdated);
        ps.setString(10, lastUpdatedby);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contact);
        ps.setInt(14, appointmentId);
        //Execute the SQL query and retrieve number of row affected
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This is the delete method.
     * This method deletes an appointment record from the appointments table based on the provided appointment ID.
     * @param id The ID of the appointment to be deleted.
     * @return The number of rows affected by the delete operation.*/
    public static int delete(int id) throws SQLException {
        //SQL query to delete an appointment based on ID.
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Set the ID parameter for the SQL query
        ps.setInt(1, id);
        //Execute the SQL query.
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**This is the getContactIDByName method.
     * This method gets the contact ID based on the provided contact name.
     * @param contactName The name of the contact.
     * @return The contact ID of the contact.*/
    public static int getContactIDByName(String contactName) throws SQLException {
        // Default value if not found
        int contactID = -1;
        //SQL query to select the contact ID based on the contact name
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            //Set the contact name parameter for the SQL
            ps.setString(1, contactName);
            //Execute the SQL query and retrieve the result set
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    //retrieve the contact ID from the result set
                    contactID = resultSet.getInt("Contact_ID");
                }
            }
        }

        return contactID;
    }

    /**This is the getContactNamebyID method.
     * This method retrieves the contact named based on the provided contact ID.
     * @param contactID The ID of the contact.
     * @return The name of the contact.*/
    public static String getContactNamebyID(int contactID) throws SQLException {
        //SQL query to select the contact name based on the contact ID.
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        String contactName = null;
        //Execute the SQL query and retrieve the result set.
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, contactID);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    //retrieve the contact name from the result set.
                    contactName = resultSet.getString("Contact_Name");
                }
            }
        }

        return contactName;
    }

    /**This is the getAppointmentsBetween method.
     * This method retrieves a list of appointments between the provided start and end times.
     * @param startTime The provided start time.
     * @param endTime The provided end time.
     * @return A list of the appointments formatted as strings.*/
    public static List<String> getAppointmentsBetween(String startTime, String endTime) throws SQLException {
        //List to store appointment strings
        List<String> appointments = new ArrayList<>();
        //SQL query to select appointments between start and end time.
        String sql = "SELECT * FROM appointments WHERE Start >= ? AND Start <= ?";


        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            //Set the start and end time parameters
            preparedStatement.setString(1, startTime);
            preparedStatement.setString(2, endTime);
            //Execute the SQL query and retrieve the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                //Iterate over the result set and add appointments to the list
                while (resultSet.next()) {
                    String appointmentInfo = String.format(
                            "Appointment_ID: %d, Title: %s, Start: %s, End: %s",
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Start"),
                            resultSet.getString("End"));

                    appointments.add(appointmentInfo);
                }
            }
        }

        return appointments;
    }

}
