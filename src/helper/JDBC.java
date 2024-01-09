package helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**This is the JDBC class.
 *The JDBC class provides methods for managing a connection to the MySQL database.
 *It includes methods for opening and closing a database connection.
 */
public abstract class JDBC {

    /**The protocol used for JDBC connection. */
    private static final String protocol = "jdbc";

    /**The vendor specific part of the JDBC URL. */
    private static final String vendor = ":mysql:";

    /**The location of the database server in the JDBC URL. */
    private static final String location = "//localhost/";

    /**The name of the database in the JDBC URL. */
    private static final String databaseName = "client_schedule";

    /**The complete JDBC URL for connectivity to the database. */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    /**The JDBC driver class reference. */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference

    /**The username for the JDBC connection. */
    private static final String userName = "sqlUser"; // Username

    /**The password for the JDBC connection*/
    private static String password = "Passw0rd!"; // Password

    /**The connection interface for the JDBC connnection. */
    public static Connection connection;  // Connection Interface

    /**This is the openConnection method.
     *It opens a connection to the MySQL database. */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**This is the closeConnection method.
     *It closes a connection to the MySql database. */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
