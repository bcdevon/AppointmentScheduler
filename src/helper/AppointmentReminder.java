package helper;

import DAO.AppointmentQuery;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**This is the Appointment Reminder class. This class provides a method for checking for upcoming appointments
 * within the 15 minutes of the current time.
 * */
public class AppointmentReminder {

    /**Check for upcoming appointments within the next 15 minutes*/
    public static void checkAppointments(){
        try{
            //get the current time
            LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
            //add 15 min to current time to get the end time
            LocalDateTime endTime = currentDateTime.plusMinutes(15);
            //Format the time to match database
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentTimeFormatted = currentDateTime.format(formatter);
            String endTimeFormatted = endTime.format(formatter);

            //check database for appointments between the current time and end time
            List<String> upcomingAppointments = AppointmentQuery.getAppointmentsBetween(currentTimeFormatted, endTimeFormatted);
            String alertContentText ;
            if (upcomingAppointments.isEmpty()){
                alertContentText = "No upcoming appointments within the next 15 minutes.";
            } else {
                alertContentText = "Warning: You have upcoming appointments within the next 15 minutes.";
                //add upcoming appointment details to alert message.
                for (String appointment : upcomingAppointments) {
                    alertContentText += "\n" + appointment;
                }
            }
            //display appropriate alert message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Appointment warning");
            alert.setContentText(alertContentText);
            alert.showAndWait();
            //print the alert message
            System.out.println(alertContentText);


//            if (!upcomingAppointments.isEmpty()) {
//                // Notify the user about upcoming appointments
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Appointment warning");
//                alert.setContentText("Warning: You have upcoming appointments within the next 15 minutes.");
//                alert.showAndWait();
//                System.out.println("Warning: You have upcoming appointments within the next 15 minutes.");
//
//
//                for (String appointment : upcomingAppointments) {
//                    System.out.println(appointment);
//                }
//            } else {
//                // Notify the user about upcoming appointments
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Appointment warning");
//                alert.setContentText("No upcoming appointments within the next 15 minutes.");
//                alert.showAndWait();
//                System.out.println("No upcoming appointments within the next 15 minutes.");
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
