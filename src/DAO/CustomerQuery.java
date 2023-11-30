package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class CustomerQuery {
    public static void select() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            int divisionId = rs.getInt("Division_ID");
            System.out.println(id + " " + name + " " + address + " " + postal + " " + divisionId);
        }
    }
    public static int insert(String name, String address, String postal, String phone, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int divisionId) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1, id);
        ps.setString(1, name );
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(6, createdBy);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(8, lastUpdatedby);
        ps.setInt(9, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
        }
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
        }


    public static int update(int customerIDS, String nameS, String addressS, String postalS, String phoneS, String createdateS, String createdbyS, String lastUpdatedS, String lastUpdateByS, String countryS, String divisionS) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name =?, Address =?, Postal_Code =?, Phone =?, Create_Date =?, Created_By =?, Last_Update =?, Last_Updated_By =?, Division_ID =? WHERE Customer_ID = ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerIDS);
        ps.setString(2, nameS);
        ps.setString(3, addressS);
        ps.setString(4, postalS);
        ps.setString(5, phoneS);
        ps.setString(6, createdateS);
        ps.setString(7, createdbyS);
        ps.setString(8, lastUpdatedS);
        ps.setString(9, lastUpdateByS);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}



