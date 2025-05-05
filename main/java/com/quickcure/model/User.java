package com.quickcure.model;

public class User {
    private int userId;            // User ID, added as primary key field
    private String userName;
    private String userAddress;
    private String userGender;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String userRole;
    private String firstName;
    private String lastName;

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(int userId, String firstName, String lastName, String userName, String userAddress, String userGender,
                String userEmail, String userPhone, String userPassword, String userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    // Getters and setters for all fields

    public int getUserId() {
        return userId;  
    }

    public void setUserId(int userId) {
        this.userId = userId;  
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
