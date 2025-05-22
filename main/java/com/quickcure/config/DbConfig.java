package com.quickcure.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

/**
 * DbConfig manages the connection to the database.
 */
@WebListener
public class DbConfig implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DbConfig.class.getName());

    // Database configuration
    private static final String DB_NAME = "quickcure";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; 

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver registered successfully");
            // Test the connection
            try (Connection testConn = getDbConnection()) {
                LOGGER.info("Database connection test successful");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            // Deregister JDBC driver
            java.sql.Driver driver = DriverManager.getDriver(URL);
            DriverManager.deregisterDriver(driver);
            LOGGER.info("MySQL JDBC Driver deregistered successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deregistering JDBC driver", e);
        }
    }

    /**
     * Returns a new database connection.
     *
     * @return a Connection object
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            LOGGER.fine("Database connection established successfully");
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection", e);
            throw e;
        }
    }
    

}
