package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CustomerQuery {

        public static int insert(int, int colorId) throws SQLException {
            String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES(?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, fruitName);
            ps.setInt(2, colorId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
}
