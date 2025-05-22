package com.quickcure.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling errors and exceptions
 */
public class ErrorUtil {
    private static final Logger LOGGER = Logger.getLogger(ErrorUtil.class.getName());
    
    /**
     * Handle an exception and forward to an error page
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     * @param exception The exception to handle
     * @param errorPage The error page to forward to
     * @throws ServletException If a servlet exception occurs
     * @throws IOException If an I/O exception occurs
     */
    public static void handleException(HttpServletRequest request, HttpServletResponse response, 
                                      Exception exception, String errorPage) 
            throws ServletException, IOException {
        
        LOGGER.log(Level.SEVERE, "Error occurred", exception);
        
        request.setAttribute("error", exception.getMessage());
        request.setAttribute("exception", exception);
        
        request.getRequestDispatcher(errorPage).forward(request, response);
    }
    
    /**
     * Handle an error with a custom message and forward to an error page
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     * @param errorMessage The error message
     * @param errorPage The error page to forward to
     * @throws ServletException If a servlet exception occurs
     * @throws IOException If an I/O exception occurs
     */
    public static void handleError(HttpServletRequest request, HttpServletResponse response, 
                                  String errorMessage, String errorPage) 
            throws ServletException, IOException {
        
        LOGGER.log(Level.WARNING, "Error: {0}", errorMessage);
        
        request.setAttribute("error", errorMessage);
        
        request.getRequestDispatcher(errorPage).forward(request, response);
    }
    
    /**
     * Log an exception without forwarding to an error page
     * 
     * @param exception The exception to log
     * @param message Additional message to log
     */
    public static void logException(Exception exception, String message) {
        LOGGER.log(Level.SEVERE, message, exception);
    }
    
    /**
     * Check if a required parameter exists and is not empty
     * 
     * @param request The HTTP request
     * @param paramName The parameter name
     * @return true if the parameter exists and is not empty, false otherwise
     */
    public static boolean validateRequiredParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validate that all required parameters exist and are not empty
     * 
     * @param request The HTTP request
     * @param paramNames The parameter names
     * @return true if all parameters exist and are not empty, false otherwise
     */
    public static boolean validateRequiredParameters(HttpServletRequest request, String... paramNames) {
        for (String paramName : paramNames) {
            if (!validateRequiredParameter(request, paramName)) {
                return false;
            }
        }
        return true;
    }
}
