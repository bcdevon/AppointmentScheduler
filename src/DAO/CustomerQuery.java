package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery {

//        public static int insert(int, int colorId) throws SQLException {
//            String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES(?, ?)";
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, fruitName);
//            ps.setInt(2, colorId);
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected;
//        }
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
    public static void insert(int id, String name, String address, String postal, String phone, String createDate, String createdBy, String lastUpdated, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.

        }
    }
}
