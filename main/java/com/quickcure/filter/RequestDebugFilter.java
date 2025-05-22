package com.quickcure.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class RequestDebugFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(RequestDebugFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURL().toString();
        String queryString = httpRequest.getQueryString();
        
        LOGGER.info("Incoming request: " + url + 
                   (queryString != null ? "?" + queryString : "") +
                   " (Method: " + httpRequest.getMethod() + ")");
        
        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            LOGGER.info("Request completed in " + (endTime - startTime) + "ms");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
} 