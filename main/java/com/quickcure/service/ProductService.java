package com.quickcure.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.quickcure.config.DbConfig;
import com.quickcure.model.Product;

public class ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
    private static final int PRODUCTS_PER_PAGE = 8;

    /**
     * Retrieves a product by ID.
     */
    public Product getProductById(int productId) {
        String query = "SELECT * FROM product WHERE Product_id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractProductFromResultSet(rs);
                }
            }

            LOGGER.info("No product found with ID: " + productId);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving product with ID: " + productId, e);
        }

        return null;
    }

    /**
     * Retrieves all products without pagination.
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product ORDER BY Product_id";

        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
            LOGGER.info("Retrieved " + products.size() + " products");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all products", e);
        }

        return products;
    }

    /**
     * Retrieves products by search query with pagination
     */
    public List<Product> getProductsBySearch(String query, int page) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * PRODUCTS_PER_PAGE;
        String sql = "SELECT * FROM product WHERE Product_name LIKE ? OR Product_description LIKE ? " +
                    "ORDER BY Product_id LIMIT ? OFFSET ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            stmt.setInt(3, PRODUCTS_PER_PAGE);
            stmt.setInt(4, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(extractProductFromResultSet(rs));
                }
            }
            LOGGER.info("Search found " + products.size() + " products for page " + page);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error searching products with query: " + query, e);
        }
        return products;
    }

    /**
     * Creates a new product.
     */
    public boolean createProduct(Product product) {
        String query = "INSERT INTO product (Product_name, Product_price, Product_type, Product_description, " +
                      "Product_manufacturer, Product_expiry_date, Product_image, Product_stock_status, " +
                      "Product_manufacture_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setString(3, product.getProductType());
            stmt.setString(4, product.getProductDescription());
            stmt.setString(5, product.getProductManufacturer());
            stmt.setDate(6, new java.sql.Date(product.getProductExpiryDate().getTime()));
            stmt.setString(7, product.getProductImage());
            stmt.setString(8, product.getProductStockStatus());
            stmt.setDate(9, new java.sql.Date(product.getProductManufactureDate().getTime()));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing product.
     */
    public boolean updateProduct(Product product) {
        String query = "UPDATE product SET Product_name = ?, Product_price = ?, Product_type = ?, " +
                      "Product_description = ?, Product_manufacturer = ?, Product_expiry_date = ?, " +
                      "Product_image = ?, Product_stock_status = ?, Product_manufacture_date = ? " +
                      "WHERE Product_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setString(3, product.getProductType());
            stmt.setString(4, product.getProductDescription());
            stmt.setString(5, product.getProductManufacturer());
            stmt.setDate(6, new java.sql.Date(product.getProductExpiryDate().getTime()));
            stmt.setString(7, product.getProductImage());
            stmt.setString(8, product.getProductStockStatus());
            stmt.setDate(9, new java.sql.Date(product.getProductManufactureDate().getTime()));
            stmt.setInt(10, product.getProductId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a product by ID.
     */
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE Product_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates product stock status.
     */
    public boolean updateProductStockStatus(int productId, String stockStatus) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setProductStockStatus(stockStatus);
            return updateProduct(product);
        }
        return false;
    }

    /**
     * Validates product input for creation or update.
     */
    public String validateProductInput(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return "Product name is required";
        }
        if (product.getProductPrice() <= 0) {
            return "Product price must be greater than zero";
        }
        if (product.getProductType() == null || product.getProductType().trim().isEmpty()) {
            return "Product type is required";
        }
        if (product.getProductDescription() == null || product.getProductDescription().trim().isEmpty()) {
            return "Product description is required";
        }
        if (product.getProductManufacturer() == null || product.getProductManufacturer().trim().isEmpty()) {
            return "Product manufacturer is required";
        }
        if (product.getProductExpiryDate() == null) {
            return "Product expiry date is required";
        }
        if (product.getProductManufactureDate() == null) {
            return "Product manufacture date is required";
        }
        if (product.getProductManufactureDate().after(product.getProductExpiryDate())) {
            return "Manufacture date cannot be after expiry date";
        }
        return null;
    }

    /**
     * Helper method to extract a Product object from a ResultSet.
     */
    public Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("Product_id"));
        product.setProductName(rs.getString("Product_name"));
        product.setProductDescription(rs.getString("Product_description"));
        product.setProductManufacturer(rs.getString("Product_manufacturer"));
        product.setProductType(rs.getString("Product_type"));
        product.setProductImage(rs.getString("Product_image"));
        product.setProductPrice(rs.getDouble("Product_price"));
        product.setProductStockStatus(rs.getString("Product_stock_status"));

        // Handle dates if they exist in the result set
        try {
            if (rs.getDate("Product_manufacture_date") != null) {
                product.setProductManufactureDate(rs.getDate("Product_manufacture_date"));
            }
            if (rs.getDate("Product_expiry_date") != null) {
                product.setProductExpiryDate(rs.getDate("Product_expiry_date"));
            }
        } catch (SQLException e) {
            // Ignore if date fields don't exist
        }

        return product;
    }

    /**
     * Gets the total number of products for pagination
     */
    public int getTotalProducts() {
        String query = "SELECT COUNT(*) FROM product";
        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error getting total product count", e);
        }
        return 0;
    }

    /**
     * Gets products for a specific page
     */
    public List<Product> getProductsByPage(int page) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * PRODUCTS_PER_PAGE;
        String query = "SELECT * FROM product ORDER BY Product_id LIMIT ? OFFSET ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, PRODUCTS_PER_PAGE);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(extractProductFromResultSet(rs));
                }
            }
            LOGGER.info("Retrieved " + products.size() + " products for page " + page);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving products for page " + page, e);
        }

        return products;
    }

    /**
     * Gets total number of products matching a search query
     */
    public int getTotalProductsBySearch(String query) {
        String sql = "SELECT COUNT(*) FROM product WHERE Product_name LIKE ? OR Product_description LIKE ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error getting total products for search: " + query, e);
        }
        return 0;
    }

    public boolean removeProduct(int productId) {
        String sql = "DELETE FROM product WHERE Product_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to remove product", e);
        }
    }

    public Product getProduct(int productId) {
        String sql = "SELECT * FROM product WHERE Product_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("Product_id"));
                    product.setProductName(rs.getString("Product_name"));
                    product.setProductPrice(rs.getDouble("Product_price"));
                    product.setProductType(rs.getString("Product_type"));
                    product.setProductStockStatus(rs.getString("Product_stock_status"));
                    return product;
                }
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch product", e);
        }
    }
}