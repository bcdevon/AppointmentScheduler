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

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Appointments");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //test for french
//        Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();
        launch(args);
        int rowsAffected = CustomerQuery.insert(9, "jane doe", "1234 blue street", "876-5309", "911", "1987-03-20 09:44:22", "steve", "1999-03-20 09:44:22", "tyler", 5 );
        JDBC.closeConnection();
    }

}
