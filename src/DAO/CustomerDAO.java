package DAO;
import Model.Customer;
import helper.JDBC;

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
    //left off here create function to add a customer to the myql database
    public static String addCustomer()
}
