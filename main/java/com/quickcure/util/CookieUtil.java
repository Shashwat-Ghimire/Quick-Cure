package com.quickcure.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for handling cookies
 */
public class CookieUtil {
    
    /**
     * Add a cookie to the response
     * 
     * @param response The HTTP response
     * @param name The cookie name
     * @param value The cookie value
     * @param maxAge The maximum age of the cookie in seconds, or -1 for session cookie
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // For security, prevent JavaScript access
        response.addCookie(cookie);
    }
    
    /**
     * Get a cookie value by name
     * 
     * @param request The HTTP request
     * @param name The cookie name
     * @return The cookie value, or null if not found
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * Delete a cookie
     * 
     * @param response The HTTP response
     * @param name The cookie name
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * Check if a cookie exists
     * 
     * @param request The HTTP request
     * @param name The cookie name
     * @return true if the cookie exists, false otherwise
     */
    public static boolean cookieExists(HttpServletRequest request, String name) {
        return getCookieValue(request, name) != null;
    }
    
    /**
     * Set a remember-me cookie
     * 
     * @param response The HTTP response
     * @param userId The user ID
     * @param token A secure token
     */
    public static void setRememberMeCookie(HttpServletResponse response, String userId, String token) {
        // Set cookie to expire in 30 days
        addCookie(response, "remember_user", userId, 30 * 24 * 60 * 60);
        addCookie(response, "remember_token", token, 30 * 24 * 60 * 60);
    }
    
    /**
     * Clear remember-me cookies
     * 
     * @param response The HTTP response
     */
    public static void clearRememberMeCookies(HttpServletResponse response) {
        deleteCookie(response, "remember_user");
        deleteCookie(response, "remember_token");
    }
}
