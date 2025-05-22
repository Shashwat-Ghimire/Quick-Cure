package com.quickcure.model;

import java.io.Serializable;
import java.util.Date;

/**
 * PaymentModel class representing payment data from the database
 */
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int paymentId;
    private String paymentMethod;
    private double paymentAmount;
    private Date paymentDate;
    private int userId;
    private int orderId;
    
    // Default constructor
    public PaymentModel() {
    }
    
    // Parameterized constructor
    public PaymentModel(int paymentId, String paymentMethod, double paymentAmount, Date paymentDate, int userId, int orderId) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.orderId = orderId;
    }
    
    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    @Override
    public String toString() {
        return "PaymentModel{" +
                "paymentId=" + paymentId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", paymentDate=" + paymentDate +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }
}
