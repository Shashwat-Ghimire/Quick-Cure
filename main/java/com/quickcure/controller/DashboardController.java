package com.quickcure.controller;

import com.quickcure.model.DashboardModel;
import com.quickcure.service.DashboardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = { "/DashboardController" })
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            DashboardModel dashboardModel = new DashboardModel();

            dashboardModel.setTotalOrders(dashboardService.getTotalOrders());
            dashboardModel.setTotalRevenue(dashboardService.getTotalRevenue());
            dashboardModel.setActiveCustomers(dashboardService.getActiveCustomers());
            dashboardModel.setLowStockItems(dashboardService.getLowStockItems());
            dashboardModel.setMonthlySales(dashboardService.getMonthlySales());
            dashboardModel.setTopProducts(dashboardService.getTopProducts());
            dashboardModel.setRecentOrders(dashboardService.getRecentOrders());

            request.setAttribute("dashboard", dashboardModel);
            request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Unable to load dashboard data. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}