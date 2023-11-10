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

public class AppointmentDAO {


    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * FROM appointments");
            String sql = "SELECT * FROM appointments";
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsByMonth(LocalDateTime startOfMonth, LocalDateTime endOfMonth) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE start >= ? AND start <= ?";

        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startOfMonth));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endOfMonth));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsForWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE start >= ? AND start <= ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startOfWeek));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endOfWeek));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    private static Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
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

        return new Appointment(id, title, description, location, type, start, end, customerID, userID, contactID);
    }
    public static ObservableList<Appointment> getAppointmentsByContactID(int contactID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, contactID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = extractAppointmentFromResultSet(resultSet);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


}
