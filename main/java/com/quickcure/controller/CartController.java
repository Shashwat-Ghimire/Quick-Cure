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
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(urlPatterns = {"/cart/*", "/cart.do"})
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CartController.class.getName());
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing GET request for cart");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            LOGGER.warning("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            LOGGER.info("Fetching cart items for user ID: " + userId);
            List<Cart> cartItems = cartService.getCartItems(userId);
            double cartTotal = cartService.getCartTotal(userId);

            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartTotal", cartTotal);
            request.getRequestDispatcher("/WEB-INF/pages/mycart.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing cart GET request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing POST request for cart");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
       
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            LOGGER.warning("User not logged in, sending error response");
            sendJsonResponse(response, false, "Please login to perform cart operations");
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            String action = request.getParameter("action");
            LOGGER.info("Cart action: " + action + " for user ID: " + userId);

            if (action == null) {
                LOGGER.warning("Missing action parameter");
                sendJsonResponse(response, false, "Invalid request: missing action parameter");
                return;
            }

            switch (action) {
                case "add":
                    handleAddToCart(request, response, userId);
                    break;
                case "update":
                    handleUpdateCart(request, response, userId);
                    break;
                case "remove":
                    handleRemoveFromCart(request, response, userId);
                    break;
                case "clear":
                    handleClearCart(request, response, userId);
                    break;
                default:
                    LOGGER.warning("Invalid action specified: " + action);
                    sendJsonResponse(response, false, "Invalid action specified");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing cart operation", e);
            sendJsonResponse(response, false, "An error occurred while processing your request");
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            LOGGER.info("Adding product ID: " + productId + " with quantity: " + quantity + " for user ID: " + userId);

            if (quantity <= 0) {
                LOGGER.warning("Invalid quantity: " + quantity);
                sendJsonResponse(response, false, "Invalid quantity");
                return;
            }

            if (cartService.isProductInCart(userId, productId)) {
                LOGGER.info("Product already in cart");
                sendJsonResponse(response, false, "This product is already in your cart");
                return;
            }

            boolean success = cartService.addToCart(userId, productId, quantity);
            LOGGER.info("Add to cart result: " + success);
            sendJsonResponse(response, success,
                success ? "Product added to cart successfully" : "Failed to add product to cart");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid parameters for add to cart", e);
            sendJsonResponse(response, false, "Invalid product ID or quantity");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding product to cart", e);
            sendJsonResponse(response, false, "An error occurred while adding the product to cart");
        }
    }

    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (quantity <= 0) {
                sendJsonResponse(response, false, "Invalid quantity");
                return;
            }

            boolean success = cartService.updateCartQuantity(cartId, userId, quantity);
            sendJsonResponse(response, success,
                success ? "Cart updated successfully" : "Failed to update cart");
        } catch (NumberFormatException e) {
            sendJsonResponse(response, false, "Invalid cart ID or quantity");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            boolean success = cartService.removeFromCart(cartId, userId);
            sendJsonResponse(response, success,
                success ? "Item removed from cart" : "Failed to remove item from cart");
        } catch (NumberFormatException e) {
            sendJsonResponse(response, false, "Invalid cart ID");
        }
    }

    private void handleClearCart(HttpServletRequest request, HttpServletResponse response, int userId)
            throws IOException {
        boolean success = cartService.clearCart(userId);
        sendJsonResponse(response, success,
            success ? "Cart cleared successfully" : "Failed to clear cart");
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
    }
} 