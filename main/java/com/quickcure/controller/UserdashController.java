package com.quickcure.controller;

import com.quickcure.model.OrderModel;
import com.quickcure.model.UserdashModel;
import com.quickcure.service.OrderService;
import com.quickcure.service.UserdashService;
import com.quickcure.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/userdash/*", "/UserdashController", "/Userdash.do" })
public class UserdashController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(UserdashController.class.getName());
    private UserdashService userdashService;
    private OrderService orderService;
    private UserService userService;
   
    @Override
    public void init() throws ServletException {
        userdashService = new UserdashService();
        orderService = new OrderService();
        userService = new UserService();
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession(false);
       
        if (session == null || session.getAttribute("currentUser") == null) {
            LOGGER.info("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }
       
        int userId = (int) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
       
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
            try {
                // Get user dashboard data
                UserdashModel userDashData = userdashService.getUserDashboardData(userId);
                List<OrderModel> recentOrders = userdashService.getRecentOrders(userId, 5);
               
                // Set attributes for the JSP
                request.setAttribute("userDashData", userDashData);
                request.setAttribute("recentOrders", recentOrders);
                request.setAttribute("userName", userName);
               
                LOGGER.info("Loading dashboard for user: " + userName + ", Orders found: " +
                           (recentOrders != null ? recentOrders.size() : 0));
               
                request.getRequestDispatcher("/WEB-INF/pages/Userdash.jsp").forward(request, response);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error loading user dashboard", e);
                response.sendRedirect(request.getContextPath() + "/error.do");
            }
        } else if (pathInfo.equals("/profile")) {
            // Show user profile
            request.getRequestDispatcher("/WEB-INF/pages/customerprofile.jsp").forward(request, response);
        } else if (pathInfo.equals("/orders")) {
            // Show user orders
            List<OrderModel> orders = orderService.getOrdersByUserId(userId);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
        } else {
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
       
        int userId = (int) session.getAttribute("userId");
       
        if (pathInfo == null) {
            pathInfo = "/";
        }
       
        if (pathInfo.equals("/update-profile")) {
            // Update user profile
            try {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
               
                boolean success = userdashService.updateUserProfile(userId, firstName, lastName, address, phone, email);
               
                if (success) {
                    // Update session attributes if needed
                    session.setAttribute("userFirstName", firstName);
                    session.setAttribute("userLastName", lastName);
                   
                    request.setAttribute("message", "Profile updated successfully");
                } else {
                    request.setAttribute("error", "Failed to update profile");
                }
               
                request.getRequestDispatcher("/WEB-INF/pages/customerprofile.jsp").forward(request, response);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error updating profile", e);
                request.setAttribute("error", "Error: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/customerprofile.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}