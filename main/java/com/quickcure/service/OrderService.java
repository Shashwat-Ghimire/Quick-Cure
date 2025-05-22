package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.OrderModel;
import com.quickcure.model.Product;
import com.quickcure.model.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OrderService class for handling order-related operations
 */
public class OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    /**
     * Create a new order
     * @param order The order to create
     * @return The ID of the created order, or -1 if failed
     */
    public int createOrder(OrderModel order) {
        String orderQuery = "INSERT INTO orders (Users_id, Orders_total_amount, Orders_date, Orders_status) VALUES (?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO order_items (Orders_id, Product_id, Quantity, Price) VALUES (?, ?, ?, ?)";
        String userOrderQuery = "INSERT INTO users_orders (Users_id, Orders_id) VALUES (?, ?)";
        
        try (Connection conn = DbConfig.getDbConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDouble(2, order.getTotalAmount());
                orderStmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
                orderStmt.setString(4, order.getStatus());
                
                int affectedRows = orderStmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }
                
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        
                        // Insert into users_orders table
                        try (PreparedStatement userOrderStmt = conn.prepareStatement(userOrderQuery)) {
                            userOrderStmt.setInt(1, order.getUserId());
                            userOrderStmt.setInt(2, orderId);
                            userOrderStmt.executeUpdate();
                        }
                        
                        // Insert order items
                        try (PreparedStatement itemStmt = conn.prepareStatement(orderItemQuery)) {
                            for (Product product : order.getProducts()) {
                                itemStmt.setInt(1, orderId);
                                itemStmt.setInt(2, product.getProductId());
                                itemStmt.setInt(3, 1); // Default quantity to 1 for now
                                itemStmt.setDouble(4, product.getProductPrice());
                                itemStmt.addBatch();
                            }
                            itemStmt.executeBatch();
                        }
                        
                        conn.commit();
                        return orderId;
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating order", e);
        }
        
        return -1;
    }
    
    /**
     * Get order by ID
     * @param orderId The ID of the order to retrieve
     * @return OrderModel if found, null otherwise
     */
    public OrderModel getOrderById(int orderId) {
        String orderQuery = "SELECT o.* FROM orders o WHERE o.Orders_id = ?";
        String itemsQuery = "SELECT p.* FROM order_items oi " +
                           "JOIN product p ON oi.Product_id = p.Product_id " +
                           "WHERE oi.Orders_id = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
             PreparedStatement itemsStmt = conn.prepareStatement(itemsQuery)) {
            
            orderStmt.setInt(1, orderId);
            itemsStmt.setInt(1, orderId);
            
            try (ResultSet orderRs = orderStmt.executeQuery()) {
                if (orderRs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(orderRs.getInt("Orders_id"));
                    order.setUserId(orderRs.getInt("Users_id"));
                    order.setTotalAmount(orderRs.getDouble("Orders_total_amount"));
                    order.setOrderDate(orderRs.getTimestamp("Orders_date"));
                    order.setStatus(orderRs.getString("Orders_status"));
                    
                    List<Product> products = new ArrayList<>();
                    try (ResultSet itemsRs = itemsStmt.executeQuery()) {
                        ProductService productService = new ProductService();
                        while (itemsRs.next()) {
                            products.add(productService.extractProductFromResultSet(itemsRs));
                        }
                    }
                    order.setProducts(products);
                    
                    return order;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving order with ID: " + orderId, e);
        }
        
        return null;
    }
    
    /**
     * Get all orders for a user
     * @param userId The ID of the user
     * @return List of orders for the user
     */
    public List<OrderModel> getOrdersByUserId(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT o.* FROM orders o " +
                      "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                      "WHERE uo.Users_id = ? " +
                      "ORDER BY o.Orders_date DESC";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(rs.getInt("Orders_id"));
                    order.setUserId(userId);
                    order.setTotalAmount(rs.getDouble("Orders_total_amount"));
                    order.setOrderDate(rs.getTimestamp("Orders_date"));
                    order.setStatus(rs.getString("Orders_status"));
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving orders for user: " + userId, e);
        }
        
        return orders;
    }
    
    /**
     * Update order status
     * @param orderId The ID of the order to update
     * @param status The new status
     * @return true if successful, false otherwise
     */
    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET Orders_status = ? WHERE Orders_id = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating order status", e);
            return false;
        }
    }
    
    /**
     * Get all orders
     * @return List of all orders
     */
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT o.*, uo.Users_id FROM orders o " +
                     "JOIN users_orders uo ON o.Orders_id = uo.Orders_id " +
                     "ORDER BY o.Orders_date DESC";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("Orders_id"));
                order.setTotalAmount(rs.getDouble("Orders_total_amount"));
                order.setOrderDate(rs.getDate("Orders_date"));
                order.setStatus(rs.getString("Orders_status"));
                order.setUserId(rs.getInt("Users_id"));
                
                // Get products for this order
                order.setProducts(getProductsForOrder(order.getOrderId(), conn));
                
                orders.add(order);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * Helper method to get products for an order
     * @param orderId The ID of the order
     * @param conn The database connection
     * @return List of products in the order
     * @throws SQLException If a database access error occurs
     */
    private List<Product> getProductsForOrder(int orderId, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM product p " +
                     "JOIN orders_product op ON p.Product_id = op.Product_id " +
                     "WHERE op.Orders_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("Product_id"));
                    product.setProductName(rs.getString("Product_name"));
                    product.setProductPrice(rs.getDouble("Product_price"));
                    product.setProductImage(rs.getString("Product_image"));
                    product.setProductType(rs.getString("Product_type"));
                    product.setProductDescription(rs.getString("Product_description"));
                    product.setProductManufacturer(rs.getString("Product_manufacturer"));
                    product.setProductStockStatus(rs.getString("Product_stock_status"));
                    
                    products.add(product);
                }
            }
        }
        
        return products;
    }

    /**
     * Creates a new order from cart items
     * 
     * @param order Basic order information
     * @param cartItems Cart items to include in the order
     * @return The new order ID or -1 if failed
     */
    public int createOrder(OrderModel order, List<Cart> cartItems) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int orderId = -1;

        try {
            conn = DbConfig.getDbConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert order
            String sql = "INSERT INTO orders (user_id, total_amount, order_date, status) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            pstmt.setString(4, order.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get the auto-generated order ID
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                    
                    // Insert order items
                    pstmt.close();
                    sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    
                    for (Cart item : cartItems) {
                        pstmt.setInt(1, orderId);
                        pstmt.setInt(2, item.getProductId());
                        pstmt.setInt(3, item.getQuantity());
                        pstmt.setDouble(4, item.getProductPrice());
                        pstmt.addBatch();
                    }
                    
                    pstmt.executeBatch();
                    conn.commit(); // Commit transaction
                    LOGGER.info("Order created successfully with ID: " + orderId);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating order", e);
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException se) {
                    LOGGER.log(Level.SEVERE, "Error rolling back transaction", se);
                }
            }
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return orderId;
    }

    /**
     * Closes database resources
     */
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing database resources", e);
        }
    }
}
