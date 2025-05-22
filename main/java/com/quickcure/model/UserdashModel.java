package com.quickcure.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * UserdashModel class representing user dashboard data
 */
public class UserdashModel {
    private int userId;
    private String userName;
    private int totalOrders;
    private int completedOrders;
    private double totalSpent;
    private List<OrderModel> recentOrders;
    private String lastOrderDate;
   
    // Default constructor
    public UserdashModel() {
        this.recentOrders = new ArrayList<>();
    }
   
    // Parameterized constructor
    public UserdashModel(int userId, String userName, int totalOrders, double totalSpent,
                         int completedOrders, String lastOrderDate) {
        this.userId = userId;
        this.userName = userName;
        this.totalOrders = totalOrders;
        this.totalSpent = totalSpent;
        this.completedOrders = completedOrders;
        this.lastOrderDate = lastOrderDate;
    }
   
    // Getters and setters
    public int getUserId() {
        return userId;
    }
   
    public void setUserId(int userId) {
        this.userId = userId;
    }
   
    public String getUserName() {
        return userName;
    }
   
    public void setUserName(String userName) {
        this.userName = userName;
    }
   
    public int getTotalOrders() {
        return totalOrders;
    }
   
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
   
    public int getCompletedOrders() {
        return completedOrders;
    }
   
    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }
   
    public double getTotalSpent() {
        return totalSpent;
    }
   
    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
   
    public List<OrderModel> getRecentOrders() {
        return recentOrders;
    }
   
    public void setRecentOrders(List<OrderModel> recentOrders) {
        this.recentOrders = recentOrders;
    }
   
    public String getLastOrderDate() {
        return lastOrderDate;
    }
   
    public void setLastOrderDate(String lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }
   
    @Override
    public String toString() {
        return "UserdashModel [userId=" + userId + ", userName=" + userName + ", totalOrders=" + totalOrders
                + ", totalSpent=" + totalSpent + ", completedOrders=" + completedOrders + ", lastOrderDate=" + lastOrderDate + "]";
    }
}
