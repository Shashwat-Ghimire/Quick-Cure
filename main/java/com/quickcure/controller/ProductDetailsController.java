package com.quickcure.controller;

import com.quickcure.model.Product;
import com.quickcure.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/product/*")
public class ProductDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProductDetailsController.class.getName());
    private final ProductService productService;

    public ProductDetailsController() {
        super();
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("ProductDetailsController: Handling GET request");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            LOGGER.warning("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/products.do");
            return;
        }

        try {
            // Extract product ID from path
            int productId = Integer.parseInt(pathInfo.substring(1));
            Product product = productService.getProductById(productId);

            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/pages/productdetails.jsp").forward(request, response);
            } else {
                LOGGER.warning("Product not found with ID: " + productId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid product ID format", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving product details", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving product details");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests for product actions (e.g., add to cart)
        String action = request.getParameter("action");
        if ("addToCart".equals(action)) {
            handleAddToCart(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("currentUser") == null) {
            response.getWriter().write("{\"success\": false, \"message\": \"User not logged in\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productService.getProductById(productId);
            if (product != null) {
                // Add to cart logic
                @SuppressWarnings("unchecked")
                List<Product> cart = (List<Product>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new java.util.ArrayList<>();
                    session.setAttribute("cart", cart);
                }
                cart.add(product);
                response.getWriter().write("{\"success\": true, \"message\": \"Product added to cart successfully\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Product not found\"}");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid product ID format", e);
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid product ID\"}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding product to cart", e);
            response.getWriter().write("{\"success\": false, \"message\": \"Error adding product to cart\"}");
        }
    }
} 