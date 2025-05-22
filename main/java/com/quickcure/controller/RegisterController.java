package com.quickcure.controller;

import com.quickcure.model.User;
import com.quickcure.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register.do")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("retypePassword");
        String terms = request.getParameter("terms");

        Map<String, String> errors = new HashMap<>();

        // Form validations
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "First name is required.");
        } else if (!firstName.matches("[A-Za-z\\s]{2,}")) {
            errors.put("firstName", "First name must contain only letters and spaces.");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Last name is required.");
        } else if (!lastName.matches("[A-Za-z\\s]{2,}")) {
            errors.put("lastName", "Last name must contain only letters and spaces.");
        }

        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required.");
        } else if (!username.matches("[A-Za-z0-9_]{3,20}")) {
            errors.put("username", "Username must be 3-20 characters and can contain letters, numbers, and underscores.");
        }

        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "Please enter your address");
        }

        if (gender == null || gender.trim().isEmpty() || 
            (!gender.equals("Male") && !gender.equals("Female"))) {
            errors.put("gender", "Please select a valid gender.");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required.");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            errors.put("email", "Invalid email address.");
        }

        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Phone number is required.");
        } else if (!phone.matches("^\\+?\\d{10,14}$")) {
            errors.put("phone", "Phone number must be 10-14 digits.");
        }

        if (password == null || password.length() < 6) {
            errors.put("password", "Password must be at least 6 characters.");
        }

        if (confirmPassword == null || !confirmPassword.equals(password)) {
            errors.put("retypePassword", "Passwords do not match.");
        }

        if (terms == null || !terms.equals("on")) {
            errors.put("terms", "You must agree to the terms.");
        }

        // Return if there are validation errors
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(request, response);
            return;
        }

        // Create user object
        User user = new User();
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setUserName(username.trim());
        user.setUserAddress(address.trim());
        user.setUserGender(gender);
        user.setUserEmail(email.trim().toLowerCase());
        user.setUserPhone(phone.trim());
        user.setUserPassword(password);  // Hashing will be handled in service layer
        user.setUserRole("user");        // Default role

        UserService userService = new UserService();
        boolean isRegistered = userService.registerUser(user);

        if (!isRegistered) {
            errors.put("registration", "User already exists or registration failed.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(request, response);
        } else {
            // Redirect on success
            response.sendRedirect(request.getContextPath() + "/login.do");
        }
    }
}
