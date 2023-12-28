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
     * @return The name of the division.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
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
     * @return List of country names and country IDs.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
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
     * @return ObservableList of divisions.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
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
     * @return List of division names for the country.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
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
    /**This is the getCustomerCountByDivision method.
     * This method retrieves the count of customers associated with a specific divison.
     * @param divisionId The ID of the division to count customers for.
     * @return  customerCount the number of customers in a specific division.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int getCustomerCountByDivision(int divisionId) throws SQLException {
        //Initialize customer count to default 0
        int customerCount = 0;
        try {
            //SQL statements to count customers in the specified division.
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT COUNT(*) AS CustomerCount FROM customers WHERE Division_ID = ?");
            String sql = "SELECT COUNT(*) AS customerCount FROM customers WHERE Division_ID = ?";
            //Set the divisionId as a parameter in the SQL query
            preparedStatement.setInt(1, divisionId);

            //Execute the SQL query and retrieve the customer count
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

    /**This is the getDivisionIdByName method.
     * This method gets the division ID based on the provided division name.
     * @param divisionName The name of the division.
     * @return The ID of the division.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int getDivisionIdByName(String divisionName) throws SQLException {
        //Initialize the default value of division ID
        int divisionId = -1;
        try {
            //SQL statement to select division ID based on the division name.
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, divisionName);

            //Execute the SQL query and retrieve the division ID
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                divisionId = resultSet.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionId;
    }

    /**This is the getAllCustomerIDs method.
     * This method retrieves a list of all the customer IDs from the database.
     * @return Observable list containing all customer IDs.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        //create an observable list to for customer IDs
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        //SQL query to select all Customer IDs from the customers table
        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Execute the query and add each customer ID to the list
        ResultSet resultSet = ps.executeQuery();
        try {
            //Loop through the result set to extract customer IDs
            while (resultSet.next()) {
                int customerID = resultSet.getInt("Customer_ID");
                //Add the customer ID to the list of all customer IDs
                allCustomerIDs.add(customerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomerIDs;
    }

    /**This is the getAllContactNames method.
     * This method retrieves a list of all contact names from the database.
     * @return Observable List of all contact names.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static ObservableList<String> getAllContactNames() throws SQLException {
        //Create an Observable List to hold all the contact names
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        //SQL query to select all contact names from the contacts table of the database.
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        //Execute the SQL query
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            //Loop through the result set to extract contact names
            while (resultSet.next()){
                String contactName = resultSet.getString("Contact_Name");
                //add each contact name to the allContactNames list
                allContactNames.add(contactName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContactNames;
    }

    /**This is the getAllUserIDs method.
     * This method gets a list of all user IDs from the database.
     * @return ObservableList containing all user IDs
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        //Create an Observable list for all the User IDs
        ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
        //SQL query to select all user IDs from the users table
        String sql = "SELECT User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Execute the SQL query
        ResultSet resultSet = ps.executeQuery();
        try{
            //Loop through the result set to extract userIDs
            while (resultSet.next()) {
                int userID = resultSet.getInt("User_ID");
                //add each userId to the allUserIDs list
                allUserIDs.add(userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserIDs;
    }

    /**This is the getCountryIDByDivisionID method.
     * This method retrieves the country ID based on the provided division ID.
     * @param divisionID The ID of the division
     * @return The ID of the country associated with the division.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int getCountryIDByDivisionID(int divisionID) throws SQLException {
        //Initialize countryID default value
        int countryID = -1;
        //SQL query to select country ID based on the division ID
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, divisionID);
            //Execute the query and retrieve the country ID
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    countryID = resultSet.getInt("Country_ID");
                }
            }
        }

        return countryID;
    }

    /**This is getCountryByCountryID method.
     * This method gets the country name based on the provided country ID.
     * @param countryID The ID of the country.
     * @return country Name return the name of the country.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static String getCountryByCountryID(int countryID) throws SQLException {
        //Initialize the country string
        String country = null;
        //SQL query to select the name of the country based on the country ID
        String sql = "SELECT Country FROM countries WHERE Country_ID = ?";
        try (PreparedStatement ps = JDBC.connection.prepareStatement(sql)) {
            ps.setInt(1, countryID);
            //Execute the query and retrieve the country name
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    country = resultSet.getString("Country");
                }
            }
        }

        return country;
    }
}
