package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentQuery {
    public static void select() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contact = rs.getInt("Contact_ID");
            System.out.println(id + " " + title + " " + description + " " + location + " " + type + " " + customerID + " " + userID + " " + contact);
        }

    }
    public static int insert(String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int userID, int customerID, int contact) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1, id);
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
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int update(int appointmentId, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int userID, int customerID, int contact) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
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
        ps.setInt(14, appointmentId);  // Set the appointment ID for the WHERE clause
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public static int getContactIDByName(String contactName) throws SQLException {
        int contactID = -1; // Default value if not found

        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setString(1, contactName);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    contactID = resultSet.getInt("Contact_ID");
                }
            }
        }

        return contactID;
    }

    public static String getContactNamebyID(int contactID) throws SQLException {

        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        String contactName = null;
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, contactID);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    contactName = resultSet.getString("Contact_Name");
                }
            }
        }

        return contactName;
    }
    
}
