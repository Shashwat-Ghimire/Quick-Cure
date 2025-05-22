package com.quickcure.controller;

import com.quickcure.model.OrderModel;
import com.quickcure.model.PaymentModel;
import com.quickcure.model.Cart;
import com.quickcure.service.CartService;
import com.quickcure.service.OrderService;
import com.quickcure.service.PaymentService;
import com.quickcure.util.ErrorUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/payment/*", "/PaymentController" })
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());
    private PaymentService paymentService;
    private OrderService orderService;
    private CartService cartService;
    
    @Override
    public void init() throws ServletException {
        paymentService = new PaymentService();
        orderService = new OrderService();
        cartService = new CartService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }
        
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        if (pathInfo.equals("/process")) {
            // Show payment form for an order
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                OrderModel order = orderService.getOrderById(orderId);
                
                if (order != null) {
                    // Check if the order belongs to the current user
                    int userId = (int) session.getAttribute("userId");
                    
                    if (order.getUserId() == userId) {
                        request.setAttribute("order", order);
                        request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
            }
        } else {
            // Default to showing payment page with cart data
            int userId = (int) session.getAttribute("userId");
            try {
                List<Cart> cartItems = cartService.getCartItems(userId);
                double cartTotal = cartService.getCartTotal(userId);

                // Create a temporary order object for the payment page
                OrderModel tempOrder = new OrderModel();
                tempOrder.setOrderId(0); // Temporary ID
                tempOrder.setUserId(userId);
                tempOrder.setTotalAmount(cartTotal);
                tempOrder.setOrderDate(new Date());
                tempOrder.setStatus("Pending");

                request.setAttribute("order", tempOrder);
                request.setAttribute("cartItems", cartItems);
                request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing payment page request", e);
                request.setAttribute("error", "An error occurred while preparing the payment page. Please try again.");
                response.sendRedirect(request.getContextPath() + "/cart.do");
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }
        
        // Handle direct checkout from cart
        String action = request.getParameter("action");
        if ("checkout".equals(action)) {
            int userId = (int) session.getAttribute("userId");
            try {
                // Get cart items
                List<Cart> cartItems = cartService.getCartItems(userId);
                double cartTotal = cartService.getCartTotal(userId);
                
                if (cartItems == null || cartItems.isEmpty()) {
                    request.setAttribute("error", "Your cart is empty");
                    response.sendRedirect(request.getContextPath() + "/cart.do");
                    return;
                }
                
                // Create a temporary order object
                OrderModel tempOrder = new OrderModel();
                tempOrder.setOrderId(0); // Will be set when order is created
                tempOrder.setUserId(userId);
                tempOrder.setTotalAmount(cartTotal);
                tempOrder.setOrderDate(new Date());
                tempOrder.setStatus("Pending");
                
                request.setAttribute("order", tempOrder);
                request.setAttribute("cartItems", cartItems);
                request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error during checkout", e);
                request.setAttribute("error", "An error occurred during checkout. Please try again.");
                response.sendRedirect(request.getContextPath() + "/cart.do");
                return;
            }
        }
        
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        if (pathInfo.equals("/process") || pathInfo.equals("/")) {
            // Process payment for an order
            try {
                // Validate required parameters
                if (!validateRequiredParameters(request)) {
                    request.setAttribute("error", "Missing required payment information");
                    doGet(request, response);
                    return;
                }
                
                int userId = (int) session.getAttribute("userId");
                String paymentMethod = request.getParameter("paymentMethod");
                double amount = Double.parseDouble(request.getParameter("amount"));
                
                // Create an order from the cart items
                List<Cart> cartItems = cartService.getCartItems(userId);
                if (cartItems == null || cartItems.isEmpty()) {
                    request.setAttribute("error", "Your cart is empty");
                    response.sendRedirect(request.getContextPath() + "/cart.do");
                    return;
                }
                
                // Create order
                OrderModel order = new OrderModel();
                order.setUserId(userId);
                order.setTotalAmount(amount);
                order.setOrderDate(new Date());
                order.setStatus("Processing");
                
                int orderId = orderService.createOrder(order, cartItems);
                
                if (orderId <= 0) {
                    request.setAttribute("error", "Failed to create order");
                    doGet(request, response);
                    return;
                }
                
                // Create payment
                PaymentModel payment = new PaymentModel();
                payment.setPaymentMethod(paymentMethod);
                payment.setPaymentAmount(amount);
                payment.setPaymentDate(new Date());
                payment.setUserId(userId);
                payment.setOrderId(orderId);
                
                boolean success = paymentService.processPayment(payment);
                
                if (success) {
                    // Update order status
                    orderService.updateOrderStatus(orderId, "Paid");
                    
                    // Clear the cart after successful order
                    cartService.clearCart(userId);
                    
                    // Show success message
                    request.setAttribute("successMessage", "Payment successful! Your order has been placed.");
                    response.sendRedirect(request.getContextPath() + "/Userdash.do");
                } else {
                    request.setAttribute("error", "Payment processing failed");
                    OrderModel updatedOrder = orderService.getOrderById(orderId);
                    request.setAttribute("order", updatedOrder);
                    request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Invalid number format in payment processing", e);
                request.setAttribute("error", "Invalid payment information");
                request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing payment", e);
                ErrorUtil.handleException(request, response, e, "/WEB-INF/pages/payment.jsp");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private boolean validateRequiredParameters(HttpServletRequest request) {
        String paymentMethod = request.getParameter("paymentMethod");
        String amount = request.getParameter("amount");
        
        return paymentMethod != null && !paymentMethod.trim().isEmpty()
                && amount != null && !amount.trim().isEmpty();
    }
}
