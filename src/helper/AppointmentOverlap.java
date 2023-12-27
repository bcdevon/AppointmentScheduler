package helper;


import DAO.AppointmentDAO;

import Model.Appointment;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**This is the AppointmentOverlap class.
 * This class provides a method for checking if a new appointment overlaps with an existing appointment.*/
public class AppointmentOverlap {

    /**This is the appointmentOverLapChecker method.
     * This Method checks if a new appointment overlaps with existing appointments for a given customer.
     * @param appointmentId The ID of the appointment to be checked for overlap.
     * @param customerId The ID of the customer associated with the appointment.
     * @param newStartDateTime The start date and time of the new appointment.
     * @param newEndDateTime The end date and time of the new appointment.
     * @return True if the new appointment overlaps an existing appointment, False if it does not.*/
    public static boolean appointmentOverlapChecker (int appointmentId, int customerId, LocalDateTime newStartDateTime, LocalDateTime newEndDateTime) throws SQLException {
        //Retrieve existing appointments for customer
        List<Appointment> customerAppointments = AppointmentDAO.getAppointmentsByCustomerId(customerId);

        //Loop through each appointment associated with the customer
        for (Appointment existingAppointment : customerAppointments) {
            //check the existing appointment is not th same as the new appointment
            if (existingAppointment.getId() != appointmentId) {
                //get start date and time of existing appointment
                LocalDateTime existingStartDateTime = existingAppointment.getStart();
                //get end date and time of existing appointment
                LocalDateTime existingEndDateTime = existingAppointment.getEnd();

                // Check if the new appointment overlaps with an existing appointment
                if (newStartDateTime.isBefore(existingEndDateTime) && newEndDateTime.isAfter(existingStartDateTime)) {
                    // Overlapping appointments found
                    return true;
                }
            }
        }
        //overlapping appointments not found
        return false;
    }
}
