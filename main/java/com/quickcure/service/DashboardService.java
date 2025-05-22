package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.DashboardModel.Product;
import com.quickcure.model.DashboardModel.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DashboardService {

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch total orders", e);
        }
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(Orders_total_amount) FROM orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch total revenue", e);
        }
    }

    public int getActiveCustomers(int days) {
        String sql = "SELECT COUNT(DISTINCT u.Users_id) " +
                     "FROM users u " +
                     "JOIN users_orders uo ON u.Users_id = uo.Users_id " +
                     "JOIN orders o ON uo.Orders_id = o.Orders_id " +
                     "WHERE o.Orders_date >= DATE_SUB(CURRENT_DATE, INTERVAL ? DAY)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, days);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch active customers", e);
        }
    }

    // Overload for default 30 days
    public int getActiveCustomers() {
        return getActiveCustomers(30);
    }

    public int getLowStockItems() {
        String sql = "SELECT COUNT(*) FROM product WHERE Product_stock_status = 'low'";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch low stock items", e);
        }
    }

    public Map<String, Double> getMonthlySales() {
        Map<String, Double> monthlySales = new LinkedHashMap<>();
        String sql = "SELECT DATE_FORMAT(o.Orders_date, '%Y-%m') AS month, SUM(o.Orders_total_amount) AS total " +
                     "FROM orders o " +
                     "GROUP BY month ORDER BY month ASC";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                monthlySales.put(rs.getString("month"), rs.getDouble("total"));
            }
            return monthlySales;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch monthly sales", e);
        }
    }

    public List<Product> getTopProducts() {
        List<Product> topProducts = new ArrayList<>();
        String sql = "SELECT p.Product_id, p.Product_name, COUNT(op.Product_id) as sales " +
                     "FROM product p " +
                     "LEFT JOIN orders_product op ON p.Product_id = op.Product_id " +
                     "GROUP BY p.Product_id, p.Product_name " +
                     "ORDER BY sales DESC LIMIT 5";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Product_id"));
                product.setName(rs.getString("Product_name"));
                product.setSales(rs.getInt("sales"));
                topProducts.add(product);
            }
            return topProducts;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch top products", e);
        }
    }

    public List<Order> getRecentOrders() {
        List<Order> recentOrders = new ArrayList<>();
        String sql = "SELECT o.Orders_id, o.Orders_total_amount, o.Orders_date, " +
                     "CONCAT(u.First_name, ' ', u.Last_name) AS customer_name " +
                     "FROM orders o " +
                     "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                     "JOIN users u ON uo.Users_id = u.Users_id " +
                     "ORDER BY o.Orders_date DESC LIMIT 5";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("Orders_id"));
                order.setTotalAmount(rs.getDouble("Orders_total_amount"));
                order.setOrderDate(rs.getDate("Orders_date"));
                order.setCustomerName(rs.getString("customer_name"));
                recentOrders.add(order);
            }
            return recentOrders;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch recent orders", e);
        }
    }
}