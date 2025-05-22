package com.quickcure.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and verification
 */
public class PasswordUtil {
    
    private static final int SALT_LENGTH = 16;
    
    /**
     * Hash a password using SHA-256 with a random salt
     * 
     * @param password The password to hash
     * @return A string containing the salt and hashed password, separated by a colon
     */
    public static String hashPassword(String password) {
        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Hash the password with the salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Convert to Base64 for storage
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String hashedPasswordStr = Base64.getEncoder().encodeToString(hashedPassword);
            
            // Return salt:hashedPassword
            return saltStr + ":" + hashedPasswordStr;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    /**
     * Verify a password against a stored hash
     * 
     * @param password The password to verify
     * @param storedHash The stored hash (salt:hashedPassword)
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split the stored hash into salt and hashed password
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            String saltStr = parts[0];
            String storedHashedPasswordStr = parts[1];
            
            // Decode the salt and stored hashed password
            byte[] salt = Base64.getDecoder().decode(saltStr);
            
            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashedPasswordStr = Base64.getEncoder().encodeToString(hashedPassword);
            
            // Compare the hashed passwords
            return storedHashedPasswordStr.equals(hashedPasswordStr);
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Generate a secure random token
     * 
     * @param length The length of the token in bytes
     * @return A Base64-encoded random token
     */
    public static String generateToken(int length) {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[length];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }
}
