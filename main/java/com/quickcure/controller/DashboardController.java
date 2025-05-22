package com.quickcure.controller;

import com.quickcure.model.DashboardModel;
import com.quickcure.service.DashboardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/dashboard.do", "/DashboardController" })
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());
    private DashboardService dashboardService;
    
    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // For debugging purposes - allow access without session check temporarily
        // Remove or comment this in production
        boolean bypassSessionCheck = true;
        
        if ((session == null || session.getAttribute("user") == null) && !bypassSessionCheck) {
            LOGGER.info("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/login.do");
            return;
        }
        
        try {
            LOGGER.info("Loading dashboard data");
            DashboardModel dashboardModel = new DashboardModel();
            
            // Populate the dashboard model with data from service
            dashboardModel.setTotalOrders(dashboardService.getTotalOrders());
            dashboardModel.setTotalRevenue(dashboardService.getTotalRevenue());
            dashboardModel.setActiveCustomers(dashboardService.getActiveCustomers());
            dashboardModel.setLowStockItems(dashboardService.getLowStockItems());
            dashboardModel.setMonthlySales(dashboardService.getMonthlySales());
            dashboardModel.setTopProducts(dashboardService.getTopProducts());
            dashboardModel.setRecentOrders(dashboardService.getRecentOrders());
            
            // Set the dashboard model as request attribute
            request.setAttribute("dashboard", dashboardModel);
            
            // Log data for debugging
            LOGGER.info("Dashboard data loaded successfully: " + 
                      "Total Orders: " + dashboardModel.getTotalOrders() + ", " +
                      "Total Revenue: " + dashboardModel.getTotalRevenue());
            
            // Forward to the dashboard JSP
            request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading dashboard data", e);
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}