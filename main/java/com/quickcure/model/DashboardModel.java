package com.quickcure.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class DashboardModel {
    private int totalOrders;
    private double totalRevenue;
    private int activeCustomers;
    private int lowStockItems;
    private Map<String, Double> monthlySales;
    private List<Product> topProducts;
    private List<Order> recentOrders;

    public DashboardModel() {
        this.monthlySales = new HashMap<>();
        this.topProducts = new ArrayList<>();
        this.recentOrders = new ArrayList<>();
    }

    // Getters and setters
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders >= 0 ? totalOrders : 0;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue >= 0 ? totalRevenue : 0.0;
    }

    public int getActiveCustomers() {
        return activeCustomers;
    }

    public void setActiveCustomers(int activeCustomers) {
        this.activeCustomers = activeCustomers >= 0 ? activeCustomers : 0;
    }

    public int getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(int lowStockItems) {
        this.lowStockItems = lowStockItems >= 0 ? lowStockItems : 0;
    }

    public Map<String, Double> getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(Map<String, Double> monthlySales) {
        this.monthlySales = monthlySales != null ? monthlySales : new HashMap<>();
    }

    public List<Product> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<Product> topProducts) {
        this.topProducts = topProducts != null ? topProducts : new ArrayList<>();
    }

    public List<Order> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<Order> recentOrders) {
        this.recentOrders = recentOrders != null ? recentOrders : new ArrayList<>();
    }

    // Inner class for Product
    public static class Product {
        private int id;
        private String name;
        private int sales;

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
    }

    // Inner class for Order
    public static class Order {
        private int id;
        private double totalAmount;
        private Date orderDate;

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
    }
}