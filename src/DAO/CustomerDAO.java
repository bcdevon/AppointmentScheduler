package DAO;
import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * From customers");
            String sql = "SELECT * From customers";
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("Customer_ID"));
                customer.setName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostal(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    /**get all countries method
     * database query to get all the countries
     * @return all the countries*/
    public static ObservableList<String> getAllCountries() throws SQLException{
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            allCountries.add(resultSet.getString("Country"));

        }
        return allCountries;
    }

    /**get all divisions method
     * database query to get all divisions
     * @return all the divisions*/
    public static ObservableList<String> getAllDivisions() throws SQLException{
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            allDivisions.add(resultSet.getString("Division"));
        }
        return allDivisions;
    }



    //left off here create function to add a customer to the myql database
//    public static String addCustomer();

}
