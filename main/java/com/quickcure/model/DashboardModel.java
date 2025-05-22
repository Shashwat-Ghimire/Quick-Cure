package com.quickcure.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardModel {
    private int totalOrders;
    private double totalRevenue;
    private int activeCustomers;
    private int lowStockItems;
    private Map<String, Double> monthlySales = new LinkedHashMap<>();
    private List<Product> topProducts = new ArrayList<>();
    private List<Order> recentOrders = new ArrayList<>();

    // Getters and Setters
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getActiveCustomers() {
        return activeCustomers;
    }

    public void setActiveCustomers(int activeCustomers) {
        this.activeCustomers = activeCustomers;
    }

    public int getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(int lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public Map<String, Double> getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Map<String, Double> monthlySales) {
        this.monthlySales = monthlySales;
    }

    public List<Product> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<Product> topProducts) {
        this.topProducts = topProducts;
    }

    public List<Order> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<Order> recentOrders) {
        this.recentOrders = recentOrders;
    }

    // Inner classes for Product and Order
    public static class Product {
        private int id;
        private String name;
        private int sales;
        private String category; // Added to match your schema
        private double price;   // Added to match your schema

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class Order {
        private int id;
        private double totalAmount;
        private Date orderDate;
        private String customerName; // Added to store customer information
        private String status;      // Added to match your schema
        private int itemCount;      // Added to store number of items

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }
    }
}