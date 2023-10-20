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
    public static int insert(int id, String name, String address, String postal, String phone, String createDate, String createdBy, String lastUpdated, String lastUpdatedby, int divisionId) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name );
        ps.setString(3, address);
        ps.setString(4, postal);
        ps.setString(5, phone);
        ps.setString(6, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(7, createdBy);
        ps.setString(8, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(9, lastUpdatedby);
        ps.setInt(10, divisionId);
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
    }

//    public static int delete(int fruitId) throws SQLException {
//        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1, fruitId);
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected;
//    }

