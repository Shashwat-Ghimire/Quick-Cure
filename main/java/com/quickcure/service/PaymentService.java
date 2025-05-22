package com.quickcure.service;

import com.quickcure.config.DbConfig;
import com.quickcure.model.PaymentModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PaymentService class for handling payment-related operations
 */
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    
    /**
     * Process a payment
     * @param payment The payment to process
     * @return true if successful, false otherwise
     */
    public boolean processPayment(PaymentModel payment) {
        // In a real application, this would integrate with a payment gateway
        // For now, we'll just create a payment record
        return createPayment(payment) > 0;
    }
    
    /**
     * Create a payment record
     * @param payment The payment to create
     * @return The ID of the created payment, or -1 if failed
     */
    private int createPayment(PaymentModel payment) {
        String query = "INSERT INTO payment (Payment_method, Payment_amount, Payment_date, Order_id) VALUES (?, ?, ?, ?)";
        String userPaymentQuery = "INSERT INTO users_payment (Users_id, Payment_id) VALUES (?, ?)";
        
        try (Connection conn = DbConfig.getDbConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, payment.getPaymentMethod());
                stmt.setDouble(2, payment.getPaymentAmount());
                stmt.setTimestamp(3, new Timestamp(payment.getPaymentDate().getTime()));
                stmt.setInt(4, payment.getOrderId());
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Creating payment failed, no rows affected.");
                }
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int paymentId = generatedKeys.getInt(1);
                        
                        // Create user payment record
                        try (PreparedStatement userPaymentStmt = conn.prepareStatement(userPaymentQuery)) {
                            userPaymentStmt.setInt(1, payment.getUserId());
                            userPaymentStmt.setInt(2, paymentId);
                            userPaymentStmt.executeUpdate();
                        }
                        
                        conn.commit();
                        return paymentId;
                    } else {
                        throw new SQLException("Creating payment failed, no ID obtained.");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating payment", e);
        }
        
        return -1;
    }
    
    /**
     * Get payment by ID
     * @param paymentId The ID of the payment to retrieve
     * @return PaymentModel if found, null otherwise
     */
    public PaymentModel getPaymentById(int paymentId) {
        String query = "SELECT p.*, up.Users_id FROM payment p " +
                      "JOIN users_payment up ON p.Payment_id = up.Payment_id " +
                      "WHERE p.Payment_id = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, paymentId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving payment with ID: " + paymentId, e);
        }
        
        return null;
    }
    
    /**
     * Get payment by order ID
     * @param orderId The ID of the order
     * @return PaymentModel if found, null otherwise
     */
    public PaymentModel getPaymentByOrderId(int orderId) {
        String query = "SELECT p.*, up.Users_id FROM payment p " +
                      "JOIN users_payment up ON p.Payment_id = up.Payment_id " +
                      "WHERE p.Order_id = ?";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, orderId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving payment for order: " + orderId, e);
        }
        
        return null;
    }
    
    /**
     * Get payments by user ID
     * @param userId The ID of the user
     * @return List of payments for the user
     */
    public List<PaymentModel> getPaymentsByUserId(int userId) {
        List<PaymentModel> payments = new ArrayList<>();
        String query = "SELECT p.* FROM payment p " +
                      "JOIN users_payment up ON p.Payment_id = up.Payment_id " +
                      "WHERE up.Users_id = ? " +
                      "ORDER BY p.Payment_date DESC";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving payments for user: " + userId, e);
        }
        
        return payments;
    }
    
    /**
     * Extract payment from result set
     * @param rs The result set containing payment data
     * @return PaymentModel extracted from the result set
     * @throws SQLException if an error occurs while extracting payment data
     */
    private PaymentModel extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        PaymentModel payment = new PaymentModel();
        payment.setPaymentId(rs.getInt("Payment_id"));
        payment.setPaymentMethod(rs.getString("Payment_method"));
        payment.setPaymentAmount(rs.getDouble("Payment_amount"));
        payment.setPaymentDate(rs.getTimestamp("Payment_date"));
        payment.setOrderId(rs.getInt("Order_id"));
        payment.setUserId(rs.getInt("Users_id"));
        return payment;
    }
    
    /**
     * Get all payments
     * @return List of all payments
     */
    public List<PaymentModel> getAllPayments() {
        List<PaymentModel> payments = new ArrayList<>();
        String sql = "SELECT p.*, up.Users_id FROM payment p " +
                     "JOIN users_payment up ON p.Payment_id = up.Payment_id " +
                     "ORDER BY p.Payment_date DESC";
        
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PaymentModel payment = new PaymentModel();
                payment.setPaymentId(rs.getInt("Payment_id"));
                payment.setPaymentMethod(rs.getString("Payment_method"));
                payment.setPaymentAmount(rs.getDouble("Payment_amount"));
                payment.setPaymentDate(rs.getDate("Payment_date"));
                payment.setUserId(rs.getInt("Users_id"));
                
                payments.add(payment);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return payments;
    }
}
