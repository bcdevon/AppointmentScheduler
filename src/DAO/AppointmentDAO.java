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
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setContact(resultSet.getInt("Contact_ID"));
                appointment.setType(resultSet.getString("Type"));

                LocalDateTime startDateTime = resultSet.getObject("Start", LocalDateTime.class);
                LocalDateTime endDateTime = resultSet.getObject("End", LocalDateTime.class);
                appointment.setStart(startDateTime);
                appointment.setEnd(endDateTime);
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
