package com.quickcure.model;



import java.sql.Timestamp;



public class Cart {

    private int cartId;

    private int userId;

    private int productId;

    private int quantity;

    private double totalPrice;

    // Additional product details

    private String productName;

    private double productPrice;

    private String productImage;

    private Timestamp addedDate;



    public Cart() {

    }



    public Cart(int userId, int productId, int quantity) {

        this.userId = userId;

        this.productId = productId;

        this.quantity = quantity;

    }



    public int getCartId() {

        return cartId;

    }



    public void setCartId(int cartId) {

        this.cartId = cartId;

    }



    public int getUserId() {

        return userId;

    }



    public void setUserId(int userId) {

        this.userId = userId;

    }



    public int getProductId() {

        return productId;

    }



    public void setProductId(int productId) {

        this.productId = productId;

    }



    public int getQuantity() {

        return quantity;

    }



    public void setQuantity(int quantity) {

        this.quantity = quantity;

    }



    public double getTotalPrice() {

        return totalPrice;

    }



    public void setTotalPrice(double totalPrice) {

        this.totalPrice = totalPrice;

    }



    public String getProductName() {

        return productName;

    }



    public void setProductName(String productName) {

        this.productName = productName;

    }



    public double getProductPrice() {

        return productPrice;

    }



    public void setProductPrice(double productPrice) {

        this.productPrice = productPrice;

    }



    public String getProductImage() {

        return productImage;

    }



    public void setProductImage(String productImage) {

        this.productImage = productImage;

    }



    public Timestamp getAddedDate() {

        return addedDate;

    }



    public void setAddedDate(Timestamp addedDate) {

        this.addedDate = addedDate;

    }

} 