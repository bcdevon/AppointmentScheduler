package DAO;
import Model.Country;
import Model.Customer;
import helper.CountryStringConverter;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Controller.report_Controller;

/**This is the CustomerDAO class.
 * This class manages customer related database operations.
 * This class contains method for obtaining division and country details, as well as counting the number of customers in specific divisions.
 * */
public class CustomerDAO {

    /**This is the getDivisionNameByID.
     * This method gets the division name base on the provided division ID.
     * @param divisionID The id of the division.
     * @return The name of the division.*/
    public static String getDivisionNameByID(int divisionID) throws SQLException {
        //Initialize division name
        String divisionName = null;
        //SQL query to retrieve the division name based on the provided division ID
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)){
            //Set the divsion ID parameter in the SQL query
            ps.setInt(1, divisionID);
            //execute query get result set
            try (ResultSet resultSet = ps.executeQuery()){
                if (resultSet.next()){
                    //retrieve the division name from the result set
                    divisionName = resultSet.getString("Division");
                }
            }
        }
        return divisionName;
    }

    /**This is the getAllCustomers method.
     * This method gets a list of all customers from the database.
     * @return List of customers.*/
    public List<Customer> getAllCustomers(){
        //Initialize list to store customers
        List<Customer> customers = new ArrayList<>();
        try {
            //SQL query to select all customer from the database
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * From customers");
            String sql = "SELECT * From customers";
            //Execute the sql query
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            //Loop through the result set and create Customer objects
            while (resultSet.next()) {
                //Create a new Customer
                Customer customer = new Customer();
                //Set the properties of the Customer based on the result set
                customer.setId(resultSet.getInt("Customer_ID"));
                customer.setName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostal(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setDivisionId(resultSet.getInt("Division_ID"));
                customer.setDivision(getDivisionNameByID(customer.getDivisionId()));
                //add the Customer to the list
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    /**This is the get all Countries method.
     * This method get all the countries from the database and creates a list using only the countries names and IDs.
     * @return List of country names and country IDs*/
    public static List<Country> getAllCountries() throws SQLException{
        //Initialize an empty list to store Country objects
        List<Country> allCountries = new ArrayList<>();
        //SQL query to select country ID and name from the countries table
        String sql = "SELECT Country_ID, Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Execute the SQL query and retrieve the result set
        ResultSet resultSet = ps.executeQuery();
        //Loop through the result set and create country objects
        while (resultSet.next()){
            //Retrieve Country ID and name from the result set
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            //Create a new Country object and add it to the list
            allCountries.add(new Country(countryID, countryName));
        }
        return allCountries;
    }

    /**This is the getAllDivisions method.
     * This method retrieves an Observable list of all division from the database.
     * @return ObservableList of divisions*/
    public static ObservableList<String> getAllDivisions() throws SQLException{
        //Create an observable list to store division names
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        //SQL query to select division names from the first level divisions table
        String sql = "SELECT Division FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Execute the SQL query and get the result set
        ResultSet resultSet = ps.executeQuery();
        //Loop through the result set and add division names to the list
        while (resultSet.next()){
            //add the value of the division column to the allDivisions list
            allDivisions.add(resultSet.getString("Division"));
        }
        return allDivisions;
    }

    /**This is the getDivisionByCountry method.
     * This method retrieves a list of divisions based on the provided country.
     * @param country The Country for which the division are retrieved.
     * @return List of division names for the country.*/
    public static List<String> getDivisionsByCountry(Country country) throws SQLException {
        //Create a list to store division names
        List<String> divisions = new ArrayList<>();

        try {
            // Get the country ID using the CountryStringConverter
            int countryId = CountryStringConverter.getcountryAsInt(country.getName());
            //SQL query to select division names based on the country ID
            String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            //Prepare the SQL statement with the country ID parameter
            preparedStatement.setInt(1, countryId);
            //Execute the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();
            //Loop through the result set and add division names to the list
            while (resultSet.next()) {
                String division = resultSet.getString("Division");
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisions;
    }

    public static int getCustomerCountByDivision(int divisionId) throws SQLException {
        int customerCount = 0;
        try {
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT COUNT(*) AS CustomerCount FROM customers WHERE Division_ID = ?");
            String sql = "SELECT COUNT(*) AS customerCount FROM customers WHERE Division_ID = ?";
            System.out.println("SQL Query" + sql + "with parameter " + divisionId);
            preparedStatement.setInt(1, divisionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerCount = resultSet.getInt("customerCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCount;
    }
    public static int getDivisionIdByName(String divisionName) throws SQLException {
        int divisionId = -1;
        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, divisionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                divisionId = resultSet.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionId;
    }

    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        try {
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                allCustomerIDs.add(customerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomerIDs;
    }

    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            while (resultSet.next()){
                String contactName = resultSet.getString("Contact_Name");
                allContactNames.add(contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContactNames;
    }

    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
        String sql = "SELECT User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        try{
            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                allUserIDs.add(userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserIDs;
    }

    public static int getCountryIDByDivisionID(int divisionID) throws SQLException {
        int countryID = -1; // Default value indicating failure or no match

        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, divisionID);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    countryID = resultSet.getInt("Country_ID");
                }
            }
        }

        return countryID;
    }

    public static String getCountryByCountryID(int countryID) throws SQLException {
        String country = null;

        String sql = "SELECT Country FROM countries WHERE Country_ID = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, countryID);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    country = resultSet.getString("Country");
                }
            }
        }

        return country;
    }
}
