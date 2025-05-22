package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    // Register a new user without hashing
	public boolean registerUser(User user) {
	    Connection dbConn = null;
	    try {
	        dbConn = DbConfig.getDbConnection();

	        // Check if email or username already exists
	        if (isUserExists(user.getUserEmail(), user.getUserName())) {
	            return false;  // Already taken
	        }

	        String sql = "INSERT INTO users (Username, First_name, Last_name, Users_password, Users_address, Users_email, Users_phone_number, Users_role, Users_gender) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


	        PreparedStatement stmt = dbConn.prepareStatement(sql);
	        stmt.setString(1, user.getUserName());
	        stmt.setString(2, user.getFirstName());
	        stmt.setString(3, user.getLastName());
	        stmt.setString(4, user.getUserPassword()); 
	        stmt.setString(5, user.getUserAddress());   
	        stmt.setString(6, user.getUserEmail());
	        stmt.setString(7, user.getUserPhone());
	        stmt.setString(8, user.getUserRole());
	        stmt.setString(9, user.getUserGender());

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (dbConn != null) dbConn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


    // Authenticate user login without password hashing
    public User loginUser(String email, String password) {
        Connection dbConn = null;
        try {
            dbConn = DbConfig.getDbConnection();
            String sql = "SELECT Users_id, Username, First_name, Last_name, Users_email, " +
                        "Users_phone_number, Users_gender, Users_role, Users_address " +
                        "FROM users WHERE LOWER(Users_email) = LOWER(?) AND Users_password = ?";
            
            LOGGER.info("Attempting to login user with email: " + email);
            
            try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
                stmt.setString(1, email.trim());
                stmt.setString(2, password);

                LOGGER.info("Executing login query...");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setUserId(rs.getInt("Users_id"));
                        user.setUserName(rs.getString("Username"));
                        user.setUserEmail(rs.getString("Users_email"));
                        user.setUserPhone(rs.getString("Users_phone_number"));
                        user.setUserGender(rs.getString("Users_gender"));
                        user.setUserRole(rs.getString("Users_role"));
                        user.setFirstName(rs.getString("First_name"));
                        user.setLastName(rs.getString("Last_name"));
                        user.setUserAddress(rs.getString("Users_address"));
                        
                        LOGGER.info("User found with role: " + user.getUserRole() + ", ID: " + user.getUserId());
                        return user;
                    }
                    LOGGER.warning("No matching user found for email: " + email);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Database error during login", e);
        } finally {
            if (dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error closing database connection", e);
                }
            }
        }
        return null;
    }

    public boolean isUserExists(String email, String username) throws ClassNotFoundException {
        Connection dbConn = null;
        try {
            dbConn = DbConfig.getDbConnection();

            String sql = "SELECT Users_id FROM users WHERE Users_email = ? OR Username = ?";
            PreparedStatement stmt = dbConn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        } finally {
            if (dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
