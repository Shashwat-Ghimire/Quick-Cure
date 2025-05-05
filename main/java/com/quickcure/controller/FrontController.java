package com.quickcure.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String action = uri.substring(context.length());

        switch (action) {
            case "/login.do":
                request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
                break;
            case "/register.do":
                String csrfToken = UUID.randomUUID().toString();
                request.getSession().setAttribute("csrfToken", csrfToken);
                request.setAttribute("csrfToken", csrfToken);
                request.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(request, response);
                break;
            case "/dashboard.do":
                request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
                break;
            case "/Userdash.do":
                request.getRequestDispatcher("/WEB-INF/pages/Userdash.jsp").forward(request, response);
                break;
            case "/product.do":
            	request.getRequestDispatcher("/WEB-INF/pages/Product.jsp").forward(request, response);
                break;
            
            case "/settings.do":
            	request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
                break;
            case "/orders.do":
            	request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}