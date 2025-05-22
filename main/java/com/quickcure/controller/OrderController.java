package com.quickcure.controller;

import com.quickcure.model.OrderModel;
import com.quickcure.model.Product;
import com.quickcure.service.OrderService;
import com.quickcure.service.ProductService;
import com.quickcure.util.ErrorUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/order/*", "/OrderController" })
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());
    private OrderService orderService;
    private ProductService productService;
    
    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        productService = new ProductService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
        
        LOGGER.info("OrderController: Handling GET request with pathInfo: " + pathInfo);
        
        if (session == null || session.getAttribute("currentUser") == null) {
            LOGGER.warning("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // List all orders for the current user
            int userId = (int) session.getAttribute("userId");
            LOGGER.info("Fetching orders for user ID: " + userId);
            
            List<OrderModel> orders = orderService.getOrdersByUserId(userId);
            LOGGER.info("Found " + (orders != null ? orders.size() : 0) + " orders for user");
            
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/view/")) {
            // View a specific order
            try {
                int orderId = Integer.parseInt(pathInfo.substring(6));
                LOGGER.info("Fetching details for order ID: " + orderId);
                
                OrderModel order = orderService.getOrderById(orderId);
                
                if (order != null) {
                    // Check if the order belongs to the current user or if the user is an admin
                    int userId = (int) session.getAttribute("userId");
                    String userRole = (String) session.getAttribute("userRole");
                    
                    if (order.getUserId() == userId || "admin".equals(userRole)) {
                        request.setAttribute("order", order);
                        request.getRequestDispatcher("/WEB-INF/pages/orderdetails.jsp").forward(request, response);
                    } else {
                        LOGGER.warning("User " + userId + " attempted to access order " + orderId + " without permission");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    }
                } else {
                    LOGGER.warning("Order not found with ID: " + orderId);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid order ID format in URL: " + pathInfo);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
            }
        } else if (pathInfo.equals("/create")) {
            // Show create order form
            request.getRequestDispatcher("/WEB-INF/pages/createorder.jsp").forward(request, response);
        } else if (pathInfo.equals("/admin")) {
            // List all orders (admin only)
            String userRole = (String) session.getAttribute("userRole");
            if ("admin".equals(userRole)) {
                LOGGER.info("Admin user fetching all orders");
                List<OrderModel> orders = orderService.getAllOrders();
                LOGGER.info("Found " + (orders != null ? orders.size() : 0) + " total orders");
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/WEB-INF/pages/adminorders.jsp").forward(request, response);
            } else {
                LOGGER.warning("Non-admin user attempted to access admin orders page");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            }
        } else {
            LOGGER.warning("Invalid path requested: " + pathInfo);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
        
        if (pathInfo == null) {
            pathInfo = "/";
        }
        
        if (pathInfo.equals("/create")) {
            // Create a new order
            try {
                int userId = (int) session.getAttribute("userId");
                
                // Get selected products from the session or request
                @SuppressWarnings("unchecked")
                List<Product> selectedProducts = (List<Product>) session.getAttribute("cart");
                
                if (selectedProducts == null || selectedProducts.isEmpty()) {
                    request.setAttribute("error", "No products selected for order");
                    request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
                    return;
                }
                
                // Calculate total amount
                double totalAmount = 0;
                for (Product product : selectedProducts) {
                    totalAmount += product.getProductPrice();
                }
                
                // Create order
                OrderModel order = new OrderModel();
                order.setUserId(userId);
                order.setTotalAmount(totalAmount);
                order.setOrderDate(new Date());
                order.setStatus("Processing");
                order.setProducts(selectedProducts);
                
                int orderId = orderService.createOrder(order);
                
                if (orderId > 0) {
                    // Clear the cart
                    session.removeAttribute("cart");
                    
                    // Redirect to payment page
                    response.sendRedirect(request.getContextPath() + "/payment/process?orderId=" + orderId);
                } else {
                    request.setAttribute("error", "Failed to create order");
                    request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error creating order", e);
                ErrorUtil.handleException(request, response, e, "/WEB-INF/pages/cart.jsp");
            }
        } else if (pathInfo.equals("/update-status")) {
            // Update order status (admin only)
            String userRole = (String) session.getAttribute("userRole");
            if ("admin".equals(userRole)) {
                try {
                    int orderId = Integer.parseInt(request.getParameter("orderId"));
                    String status = request.getParameter("status");
                    
                    if (status == null || status.trim().isEmpty()) {
                        request.setAttribute("error", "Status cannot be empty");
                        response.sendRedirect(request.getContextPath() + "/order/admin");
                        return;
                    }
                    
                    boolean success = orderService.updateOrderStatus(orderId, status);
                    
                    if (success) {
                        request.setAttribute("message", "Order status updated successfully");
                    } else {
                        request.setAttribute("error", "Failed to update order status");
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/order/admin");
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
                }
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            }
        } else if (pathInfo.equals("/add-to-cart")) {
            // Add product to cart
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                Product product = productService.getProductById(productId);
                
                if (product != null) {
                    @SuppressWarnings("unchecked")
                    List<Product> cart = (List<Product>) session.getAttribute("cart");
                    
                    if (cart == null) {
                        cart = new ArrayList<>();
                    }
                    
                    cart.add(product);
                    session.setAttribute("cart", cart);
                    
                    response.sendRedirect(request.getContextPath() + "/product/");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            }
        } else if (pathInfo.equals("/remove-from-cart")) {
            // Remove product from cart
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                
                @SuppressWarnings("unchecked")
                List<Product> cart = (List<Product>) session.getAttribute("cart");
                
                if (cart != null) {
                    cart.removeIf(p -> p.getProductId() == productId);
                    session.setAttribute("cart", cart);
                }
                
                response.sendRedirect(request.getContextPath() + "/cart");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
