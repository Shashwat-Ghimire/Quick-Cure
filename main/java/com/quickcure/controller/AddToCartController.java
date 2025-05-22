package com.quickcure.controller;

import com.quickcure.model.Cart;
import com.quickcure.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/addtocart")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AddToCartController.class.getName());
    private CartService cartService;

    public void init() {
        cartService = new CartService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            LOGGER.warning("User not logged in, cannot add to cart");
            out.println("{\"success\": false, \"message\": \"Please login to add items to cart\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = 1; // Default quantity
            
            LOGGER.info("Adding product ID: " + productId + " to cart for user ID: " + userId);
            
            boolean success = cartService.addToCart(userId, productId, quantity);
            
            if (success) {
                LOGGER.info("Successfully added product to cart");
                out.println("{\"success\": true, \"message\": \"Product added to cart successfully\"}");
            } else {
                LOGGER.warning("Failed to add product to cart");
                out.println("{\"success\": false, \"message\": \"Failed to add product to cart\"}");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid product ID format", e);
            out.println("{\"success\": false, \"message\": \"Invalid product ID\"}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding product to cart", e);
            out.println("{\"success\": false, \"message\": \"An error occurred\"}");
        }
    }
} 