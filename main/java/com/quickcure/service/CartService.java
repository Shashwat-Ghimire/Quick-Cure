package com.quickcure.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quickcure.config.DbConfig;
import com.quickcure.model.Cart;

public class CartService {
    private Connection connection;

    public CartService() {
        try {
            connection = DbConfig.getDbConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addToCart(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart (Users_id, Product_id, Quantity) VALUES (?, ?, ?)";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
           
            return stmt.executeUpdate() > 0;
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public boolean updateCartQuantity(int cartId, int userId, int quantity) {
        String sql = "UPDATE cart SET Quantity = ? WHERE Cart_id = ? AND Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, userId);
           
            return stmt.executeUpdate() > 0;
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public List<Cart> getCartItems(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT c.Cart_id, c.Users_id, c.Product_id, c.Quantity, c.Added_date, " +
                    "p.Product_name, p.Product_price, p.Product_image, " +
                    "(p.Product_price * c.Quantity) as Total_price " +
                    "FROM cart c " +
                    "JOIN product p ON c.Product_id = p.Product_id " +
                    "WHERE c.Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
           
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cart item = new Cart();
                    item.setCartId(rs.getInt("Cart_id"));
                    item.setUserId(rs.getInt("Users_id"));
                    item.setProductId(rs.getInt("Product_id"));
                    item.setProductName(rs.getString("Product_name"));
                    item.setProductPrice(rs.getDouble("Product_price"));
                    item.setQuantity(rs.getInt("Quantity"));
                    item.setTotalPrice(rs.getDouble("Total_price"));
                    item.setProductImage(rs.getString("Product_image"));
                    item.setAddedDate(rs.getTimestamp("Added_date"));
                    cartItems.add(item);
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return cartItems;
    }
   
    public double getCartTotal(int userId) {
        String sql = "SELECT SUM(p.Product_price * c.Quantity) as cart_total " +
                    "FROM cart c " +
                    "JOIN product p ON c.Product_id = p.Product_id " +
                    "WHERE c.Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("cart_total");
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return 0.0;
    }
   
    public boolean removeFromCart(int cartId, int userId) {
        String sql = "DELETE FROM cart WHERE Cart_id = ? AND Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, cartId);
            stmt.setInt(2, userId);
           
            return stmt.executeUpdate() > 0;
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE Users_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
           
            return stmt.executeUpdate() > 0;
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public boolean isProductInCart(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE Users_id = ? AND Product_id = ?";
       
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
           
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
           
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        return false;
    }
}