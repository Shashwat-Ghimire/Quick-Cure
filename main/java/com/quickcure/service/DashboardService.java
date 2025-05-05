package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.DashboardModel.Product;
import com.quickcure.model.DashboardModel.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM order";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int totalOrders = rs.getInt(1);
                return totalOrders >= 0 ? totalOrders : 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Silently return 0 on error
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(Total_amount) FROM order";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                double totalRevenue = rs.getDouble(1);
                return totalRevenue >= 0 ? totalRevenue : 0.0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Silently return 0 on error
        }
        return 0.0;
    }

    public int getActiveCustomers() {
        String sql = "SELECT COUNT(*) FROM user WHERE active_status = true";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int activeCustomers = rs.getInt(1);
                return activeCustomers >= 0 ? activeCustomers : 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Silently return 0 on error
        }
        return 0;
    }

    public int getLowStockItems() {
        String sql = "SELECT COUNT(*) FROM product WHERE Stock_status < 10";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int lowStockItems = rs.getInt(1);
                return lowStockItems >= 0 ? lowStockItems : 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Silently return 0 on error
        }
        return 0;
    }

    public Map<String, Double> getMonthlySales() {
        Map<String, Double> monthlySales = new HashMap<>();
        String sql = "SELECT MONTHNAME(Order_date) AS month, SUM(Total_amount) AS total " +
                     "FROM order GROUP BY MONTH(Order_date)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String month = rs.getString("month");
                double total = rs.getDouble("total");
                if (total >= 0) {
                    monthlySales.put(month, total);
                }
            }
            return monthlySales;
        } catch (SQLException | ClassNotFoundException e) {
            return Collections.emptyMap();
        }
    }

    public List<Product> getTopProducts() {
        List<Product> topProducts = new ArrayList<>();
        String sql = "SELECT Product_id, Product_name, Stock_status FROM products ORDER BY Stock_status DESC LIMIT 5";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("Product_id"));
                product.setName(rs.getString("Product_name"));
                product.setSales(rs.getInt("Stock_status"));
                topProducts.add(product);
            }
            return topProducts;
        } catch (SQLException | ClassNotFoundException e) {
            return Collections.emptyList();
        }
    }

    public List<Order> getRecentOrders() {
        List<Order> recentOrders = new ArrayList<>();
        String sql = "SELECT Order_id, Total_amount, Order_date FROM orders ORDER BY Order_date DESC LIMIT 5";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("Order_id"));
                order.setTotalAmount(rs.getDouble("Total_amount"));
                order.setOrderDate(rs.getDate("Order_date"));
                recentOrders.add(order);
            }
            return recentOrders;
        } catch (SQLException | ClassNotFoundException e) {
            return Collections.emptyList();
        }
    }
}