package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.OrderModel;
import com.quickcure.model.UserdashModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserdashService class for handling user dashboard operations
 */
public class UserdashService {
   
    /**
     * Get dashboard data for a specific user
     * @param userId The ID of the user
     * @return UserdashModel containing dashboard data
     */
    public UserdashModel getUserDashboardData(int userId) {
        UserdashModel dashboard = new UserdashModel();
        dashboard.setUserId(userId);
       
        try (Connection conn = DbConfig.getDbConnection()) {
            // Get user name
            String userSql = "SELECT CONCAT(First_name, ' ', Last_name) AS userName FROM users WHERE Users_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(userSql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        dashboard.setUserName(rs.getString("userName"));
                    }
                }
            }
           
            // Get total orders and total spent
            String orderStatsSql = "SELECT COUNT(DISTINCT o.Orders_id) as totalOrders, " +
                                 "SUM(o.Orders_total_amount) as totalSpent, " +
                                 "COUNT(DISTINCT CASE WHEN o.Orders_status = 'Completed' THEN o.Orders_id END) as completedOrders " +
                                 "FROM orders o " +
                                 "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                                 "WHERE uo.Users_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(orderStatsSql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        dashboard.setTotalOrders(rs.getInt("totalOrders"));
                        dashboard.setTotalSpent(rs.getDouble("totalSpent"));
                        dashboard.setCompletedOrders(rs.getInt("completedOrders"));
                    }
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return dashboard;
    }
   
    /**
     * Get recent orders for a user
     * @param userId The ID of the user
     * @param limit The maximum number of orders to retrieve
     * @return List of recent orders
     */
    public List<OrderModel> getRecentOrders(int userId, int limit) {
        List<OrderModel> orders = new ArrayList<>();
       
        String sql = "SELECT o.Orders_id, o.Orders_total_amount, o.Orders_date, o.Orders_status " +
                    "FROM orders o " +
                    "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                    "WHERE uo.Users_id = ? " +
                    "ORDER BY o.Orders_date DESC " +
                    "LIMIT ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(rs.getInt("Orders_id"));
                    order.setTotalAmount(rs.getDouble("Orders_total_amount"));
                    order.setOrderDate(rs.getDate("Orders_date"));
                    order.setStatus(rs.getString("Orders_status"));
                    orders.add(order);
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return orders;
    }
   
    /**
     * Get order statistics for a user
     * @param userId The ID of the user
     * @return UserdashModel containing order statistics
     */
    public UserdashModel getOrderStatistics(int userId) {
        UserdashModel stats = new UserdashModel();
        stats.setUserId(userId);
       
        try (Connection conn = DbConfig.getDbConnection()) {
            // Get total orders by status
            String orderStatsSql = "SELECT o.Orders_status, COUNT(*) AS count " +
                                  "FROM orders o " +
                                  "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                                  "WHERE uo.Users_id = ? " +
                                  "GROUP BY o.Orders_status";
            try (PreparedStatement stmt = conn.prepareStatement(orderStatsSql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    int completedOrders = 0;
                   
                    while (rs.next()) {
                        String status = rs.getString("Orders_status");
                        int count = rs.getInt("count");
                       
                        if ("Delivered".equals(status)) {
                            completedOrders = count;
                        }
                    }
                   
                    stats.setCompletedOrders(completedOrders);
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return stats;
    }
   
    /**
     * Update user profile information
     * @param userId The ID of the user
     * @param firstName First name
     * @param lastName Last name
     * @param address Address
     * @param phone Phone number
     * @param email Email address
     * @return true if successful, false otherwise
     */
    public boolean updateUserProfile(int userId, String firstName, String lastName,
                                    String address, String phone, String email) {
        String sql = "UPDATE users SET First_name = ?, Last_name = ?, Users_address = ?, " +
                     "Users_phone_number = ?, Users_email = ? WHERE Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.setInt(6, userId);
           
            return stmt.executeUpdate() > 0;
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
