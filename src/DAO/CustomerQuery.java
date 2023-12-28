package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**This is the CustomerQuery class.
 * This class provides methods for performing database operations related to customers.
 * It includes methods for selecting, inserting, updating, and deleting customer records.
 * */
public abstract class CustomerQuery {
    /**This is the insert method.
     * This method inserts a new customer into the customer table in the database.
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postal The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param createDate The date and time the new customer was created.
     * @param createdBy The user that created the new customer.
     * @param lastUpdatedby The Date and time the customer record was last updated.
     * @param lastUpdated The user that last updated the customer record.
     * @param divisionId The ID of the division associated with the customer.
     * @return The number of rows affected by the insertion should be 1 if successful 0 if not.
     * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int insert(String name, String address, String postal, String phone, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int divisionId) throws SQLException {
        //Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //SQL query to insert a new customer record
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Set the parameters for the new SQL query
        ps.setString(1, name );
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(6, createdBy);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(8, lastUpdatedby);
        ps.setInt(9, divisionId);
        //Execute the SQL query and retrieve number of rows affected
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
        }

        /**This is the delete method.
         * This method deletes  a customer record from the customers table based on the customer ID.
         * @param id The ID of the customer to delete.
         *@return  The number of rows affected by the deletion.
         * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int delete(int id) throws SQLException {
        //SQL query to delete a customer based on ID
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Set the ID parameter for the SQL query
        ps.setInt(1, id);
        //Execute the SQL query and retrieve the number of row affected
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
        }

        /**This is the update method.
         * This method updates an existing customer record based on the customer ID.
         * @param customerIDS The ID of the customer to be updated.
         * @param nameS The updated name of the customer.
         * @param addressS The updated address of the customer.
         * @param postalS The update postal code of the customer.
         * @param phoneS The update phone number of the customer.
         * @param createdateS The date and time the customer record was created.
         * @param createdbyS The user that created the customer record.
         * @param lastUpdatedS The date and time the customer record was last updated.
         * @param lastUpdateByS The user that last updated the customer record.
         * @param divisionIDS The ID of the division associated with the customer.
         * @return The number of rows affected by the customer 1 if successful 0 if not.
         * @throws SQLException If a SQL exception occurs during the database operation.*/
    public static int update(int customerIDS, String nameS, String addressS, String postalS, String phoneS, String createdateS, String createdbyS, String lastUpdatedS, String lastUpdateByS, int divisionIDS) throws SQLException {
        //SQL query to update an existing customer record
        String sql = "UPDATE customers SET Customer_Name =?, Address =?, Postal_Code =?, Phone =?, Create_Date =?, Created_By =?, Last_Update =?, Last_Updated_By =?, Division_ID =? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Set the parameters for the SQL query
        ps.setString(1, nameS);
        ps.setString(2, addressS);
        ps.setString(3, postalS);
        ps.setString(4, phoneS);
        ps.setString(5, createdateS);
        ps.setString(6, createdbyS);
        ps.setString(7, lastUpdatedS);
        ps.setString(8, lastUpdateByS);
        ps.setInt(9, divisionIDS);
        ps.setInt(10,customerIDS);
        //Execute the SQL query and retrieve the number of rows affected
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}



