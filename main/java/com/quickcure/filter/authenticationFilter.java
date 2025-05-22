package com.quickcure.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { 
    "/dashboard.do", 
    "/Userdash.do",
    "/addproducts.do",
    "/removeproducts.do",
    "/updateproducts.do",
    "/product/*",
    "/cart/*",
    "/order/*"
})
public class authenticationFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();

        boolean loggedIn = (session != null && session.getAttribute("currentUser") != null);
        boolean isAdminPage = uri.contains("/dashboard.do") || 
                            uri.contains("/addproducts.do") || 
                            uri.contains("/removeproducts.do") || 
                            uri.contains("/updateproducts.do");

        if (!loggedIn) {
            res.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }

        if (isAdminPage) {
            String userRole = (String) session.getAttribute("userRole");
            if (!"admin".equals(userRole)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {}
}
