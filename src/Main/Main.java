package Main;
import DAO.CustomerQuery;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
/** This Main class is the beginning of the application.
 * This class initializes and launches the application.*/
public class Main extends Application {

    /**This is the main method.
     * This method initializes the application and opens the JDBC connection,
     * launches the application and closes the JDBC connection when the application is closed.*/
    public static void main(String[] args) throws SQLException {
        //open connection
        JDBC.openConnection();
        //launch application
        launch(args);
        //close connection when application closed
        JDBC.closeConnection();
    }

    /**This is the start method.
     * This method initializes and displays the login screen.
     * @param primaryStage The screen to be set as the primary stage in this case the login screen.*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        //load the login.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        //set the title of the primary stage
        primaryStage.setTitle("Appointments");
        primaryStage.setScene(new Scene(root, 300, 275));
        //show the primary stage
        primaryStage.show();
    }


}
