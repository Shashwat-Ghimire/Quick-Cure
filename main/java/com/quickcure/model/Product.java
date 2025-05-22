package com.quickcure.model;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productId;
    private String productName;
    private String productDescription;
    private String productManufacturer;
    private String productType;
    private String productImage;
    private double productPrice;
    private String productStockStatus;
    private Date productManufactureDate;
    private Date productExpiryDate;
    private String productCategory;
    private int productStock;

    // Default constructor
    public Product() {
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStockStatus() {
        return productStockStatus;
    }

    public void setProductStockStatus(String productStockStatus) {
        this.productStockStatus = productStockStatus;
    }

    public Date getProductManufactureDate() {
        return productManufactureDate;
    }

    public void setProductManufactureDate(Date productManufactureDate) {
        this.productManufactureDate = productManufactureDate;
    }

    public Date getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(Date productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productManufacturer='" + productManufacturer + '\'' +
                ", productType='" + productType + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productPrice=" + productPrice +
                ", productStockStatus='" + productStockStatus + '\'' +
                ", productManufactureDate=" + productManufactureDate +
                ", productExpiryDate=" + productExpiryDate +
                ", productCategory='" + productCategory + '\'' +
                ", productStock=" + productStock +
                '}';
    }
}