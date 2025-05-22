package com.quickcure.controller;

import com.quickcure.model.User;
import com.quickcure.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(urlPatterns = { "/Login", "/" })
public class Homecontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Homecontroller.class.getName());
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            String userRole = (String) session.getAttribute("userRole");
            if ("admin".equalsIgnoreCase(userRole)) {
                response.sendRedirect(request.getContextPath() + "/dashboard.do");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/Userdash.do");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LOGGER.info("Login attempt for email: " + email);

        try {
            User user = userService.loginUser(email, password);
            LOGGER.info("Login result for " + email + ": " + (user != null ? "success" : "failed"));

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentUser", user);
                session.setAttribute("userRole", user.getUserRole());
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getUserName());

                LOGGER.info("User logged in successfully. Role: " + user.getUserRole() + ", ID: " + user.getUserId());

                if ("admin".equalsIgnoreCase(user.getUserRole())) {
                    LOGGER.info("Redirecting admin to dashboard");
                    response.sendRedirect(request.getContextPath() + "/dashboard.do");
                } else {
                    LOGGER.info("Redirecting user to user dashboard");
                    response.sendRedirect(request.getContextPath() + "/Userdash.do");
                }
            } else {
                LOGGER.warning("Login failed for email: " + email);
                request.setAttribute("loginError", "Invalid email or password.");
                request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during login", e);
            request.setAttribute("loginError", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
        }
    }
}
