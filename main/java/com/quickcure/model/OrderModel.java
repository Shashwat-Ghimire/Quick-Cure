package com.quickcure.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * OrderModel class representing order data from the database
 */
public class OrderModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderId;
    private int userId;
    private double totalAmount;
    private Date orderDate;
    private String status;
    private List<Product> products;
    
    // Default constructor
    public OrderModel() {
    }
    
    // Getters and setters
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", products=" + products +
                '}';
    }
}
