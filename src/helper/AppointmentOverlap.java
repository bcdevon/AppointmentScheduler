package helper;


import DAO.AppointmentDAO;

import Model.Appointment;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentOverlap {

    public static boolean appointmentOverlapChecker (int appointmentId, int customerId, LocalDateTime newStartDateTime, LocalDateTime newEndDateTime) throws SQLException {
        //Retrieve existing appointments for customer
        List<Appointment> customerAppointments = AppointmentDAO.getAppointmentsByCustomerId(customerId);

        //check for overlapping appointments
        // Check for overlaps
        for (Appointment existingAppointment : customerAppointments) {
            if (existingAppointment.getId() != appointmentId) {
                LocalDateTime existingStartDateTime = existingAppointment.getStart();
                LocalDateTime existingEndDateTime = existingAppointment.getEnd();

                // Check if the new appointment overlaps with an existing appointment
                if (newStartDateTime.isBefore(existingEndDateTime) && newEndDateTime.isAfter(existingStartDateTime)) {
                    return true; // Overlapping appointments found
                }
            }
        }
        return false;
    }
}
