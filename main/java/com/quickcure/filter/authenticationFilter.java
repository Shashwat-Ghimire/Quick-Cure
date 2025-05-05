package com.quickcure.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { "/dashboard.jsp", "/dashboard.do", "/Userdash.do" }) // Add more if needed
public class authenticationFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (loggedIn) {
            chain.doFilter(request, response); // user is logged in, continue
        } else {
            res.sendRedirect(req.getContextPath() + "/Login"); // redirect to login
        }
    }

    public void destroy() {}
}
