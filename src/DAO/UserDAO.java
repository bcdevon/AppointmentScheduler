package DAO;
import Model.User;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    /**getAllUserIDs*
     * retrieve a list of all the user IDs fro the database
     * @return list of user IDs
     */
    public List<User> getAllUserIDs(){
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * FROM users");
            String sql = "SELECT DISTINCT User_ID FROM users";
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
