package DAO;
import java.time.LocalDateTime;

import Model.Appointment;
import helper.JDBC;

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
                appointment.setContact(resultSet.getString("Contact_ID"));
                appointment.setType(resultSet.getString("Type"));
//                appointment.setStart(resultSet.getObject());// Set other properties here
//                appointment.setEnd(resultSet.getObject());
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
