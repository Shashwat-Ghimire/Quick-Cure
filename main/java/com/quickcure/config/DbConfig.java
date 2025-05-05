package com.quickcure.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbConfig manages the connection to the database.
 */
public class DbConfig {

    // Database configuration
    private static final String DB_NAME = "quickcure";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; 

    /**
     * Returns a new database connection.
     *
     * @return a Connection object
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure JDBC driver is loaded
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    

}
