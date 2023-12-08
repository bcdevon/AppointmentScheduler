package helper;

import DAO.AppointmentQuery;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentReminder {
    public static void checkAppointments(){
        try{
            //get the current time
            LocalDateTime currentDateTime = LocalDateTime.now();

            //add 15 min to current time
            LocalDateTime endTime = currentDateTime.plusMinutes(15);

            //Format the time to match database
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTimeFormatted = currentDateTime.format(formatter);
            String endTimeFormatted = endTime.format(formatter);

            //check database for appointments in the next 15 minutes
            List<String> upcomingAppointments = AppointmentQuery.getAppointmentsBetween(currentTimeFormatted, endTimeFormatted);
            if (!upcomingAppointments.isEmpty()) {
                // Notify the user about upcoming appointments
                System.out.println("Warning: You have upcoming appointments within the next 15 minutes.");

                // You can customize this message or display it in your application's UI
                for (String appointment : upcomingAppointments) {
                    System.out.println(appointment);
                }
            } else {
                System.out.println("No upcoming appointments within the next 15 minutes.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}