package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // Register a new user without hashing
	public boolean registerUser(User user) {
	    Connection dbConn = null;
	    try {
	        dbConn = DbConfig.getDbConnection();

	        // Check if email or username already exists
	        if (isUserExists(user.getUserEmail(), user.getUserName())) {
	            return false;  // Already taken
	        }

	        String sql = "INSERT INTO user (User_name, First_name, Last_name, User_password, User_address, User_email, User_phonenumber, User_role, User_gender) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

	        PreparedStatement stmt = dbConn.prepareStatement(sql);
	        stmt.setString(1, user.getUserName());
	        stmt.setString(2, user.getFirstName());
	        stmt.setString(3, user.getLastName());
	        stmt.setString(4, user.getUserPassword()); 
	        stmt.setString(5, user.getUserAddress());   
	        stmt.setString(6, user.getUserEmail());
	        stmt.setString(7, user.getUserPhone());
	        stmt.setString(8, user.getRole());
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

            String sql = "SELECT * FROM user WHERE User_email = ? AND User_password = ?";
            PreparedStatement stmt = dbConn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password); // Compare plaintext

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("User_name"));
                user.setUserEmail(rs.getString("User_email"));
                user.setUserPhone(rs.getString("User_phonenumber"));
                user.setUserGender(rs.getString("User_gender"));
                user.setUserRole(rs.getString("User_role"));
                return user;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (dbConn != null) {
                try {
                    dbConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean isUserExists(String email, String username) throws ClassNotFoundException {
        Connection dbConn = null;
        try {
            dbConn = DbConfig.getDbConnection();

            String sql = "SELECT User_id FROM user WHERE User_email = ? OR User_name = ?";
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
