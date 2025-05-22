package com.quickcure.controller;

import com.quickcure.model.Product;
import com.quickcure.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(urlPatterns = {"/addproducts.do", "/removeproducts.do", "/updateproducts.do"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class AdminProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminProductController.class.getName());
    private final ProductService productService;
    private static final String UPLOAD_DIRECTORY = "images";

    public AdminProductController() {
        super();
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("AdminProductController: Handling GET request");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            LOGGER.warning("User not logged in, redirecting to login page");
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"admin".equals(userRole)) {
            LOGGER.warning("Non-admin user attempting to access admin functions");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String uri = request.getRequestURI();
        String jspPage;

        // Add category list for all product management pages
        List<String> categories = List.of("Antibiotics", "Pain Relief", "Vitamins", "First Aid", 
                                        "Skin Care", "Digestive Health", "Cold and Flu", 
                                        "Allergy Relief", "Medical Devices", "Personal Care");
        request.setAttribute("categoryList", categories);

        if (uri.endsWith("/addproducts.do")) {
            jspPage = "/WEB-INF/pages/addproducts.jsp";
        } else if (uri.endsWith("/removeproducts.do")) {
            List<Product> productList = productService.getAllProducts();
            request.setAttribute("productList", productList);
            jspPage = "/WEB-INF/pages/removeproducts.jsp";
        } else if (uri.endsWith("/updateproducts.do")) {
            List<Product> productList = productService.getAllProducts();
            request.setAttribute("productList", productList);
            
            String productId = request.getParameter("productId");
            if (productId != null) {
                Product selectedProduct = productService.getProductById(Integer.parseInt(productId));
                request.setAttribute("selectedProduct", selectedProduct);
            }
            
            jspPage = "/WEB-INF/pages/updateproducts.jsp";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.getRequestDispatcher(jspPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("AdminProductController: Handling POST request");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null || 
            !"admin".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    handleAddProduct(request, response);
                    break;
                case "remove":
                    handleRemoveProduct(request, response);
                    break;
                case "update":
                    handleUpdateProduct(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing product management request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error processing request: " + e.getMessage());
        }
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Product product = new Product();
        
        try {
            // Get form data
            product.setProductName(request.getParameter("productName"));
            product.setProductDescription(request.getParameter("description"));
            product.setProductPrice(Double.parseDouble(request.getParameter("price")));
            product.setProductType(request.getParameter("category"));
            product.setProductManufacturer(request.getParameter("manufacturer"));
            
            // Handle dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String manufactureDateStr = request.getParameter("manufactureDate");
                String expiryDateStr = request.getParameter("expiryDate");
                
                if (manufactureDateStr != null && !manufactureDateStr.trim().isEmpty()) {
                    product.setProductManufactureDate(dateFormat.parse(manufactureDateStr));
                }
                if (expiryDateStr != null && !expiryDateStr.trim().isEmpty()) {
                    product.setProductExpiryDate(dateFormat.parse(expiryDateStr));
                }
            } catch (ParseException e) {
                throw new ServletException("Invalid date format", e);
            }

            // Handle image upload
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                
                filePart.write(uploadPath + File.separator + fileName);
                product.setProductImage(UPLOAD_DIRECTORY + "/" + fileName);
            }

            // Set default values
            product.setProductStockStatus("in_stock");

            // Add logging for debugging
            LOGGER.info("Adding new product: " + product.getProductName() + 
                       ", Category: " + product.getProductType() + 
                       ", Price: " + product.getProductPrice());

            // Validate and save
            String validationError = productService.validateProductInput(product);
            if (validationError != null) {
                request.setAttribute("error", validationError);
                request.getRequestDispatcher("/WEB-INF/pages/addproducts.jsp").forward(request, response);
                return;
            }

            if (productService.createProduct(product)) {
                response.sendRedirect(request.getContextPath() + "/dashboard.do");
            } else {
                request.setAttribute("error", "Failed to add product");
                request.getRequestDispatcher("/WEB-INF/pages/addproducts.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid price format");
            request.getRequestDispatcher("/WEB-INF/pages/addproducts.jsp").forward(request, response);
        }
    }

    private void handleRemoveProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        if (productService.deleteProduct(productId)) {
            response.sendRedirect(request.getContextPath() + "/dashboard.do");
        } else {
            request.setAttribute("error", "Failed to remove product");
            request.getRequestDispatcher("/WEB-INF/pages/removeproducts.jsp").forward(request, response);
        }
    }

    private void handleUpdateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productService.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            // Update product fields
            product.setProductName(request.getParameter("productName"));
            product.setProductDescription(request.getParameter("description"));
            product.setProductPrice(Double.parseDouble(request.getParameter("price")));
            product.setProductType(request.getParameter("category"));
            product.setProductManufacturer(request.getParameter("manufacturer"));

            // Handle dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String manufactureDateStr = request.getParameter("manufactureDate");
                String expiryDateStr = request.getParameter("expiryDate");
                
                if (manufactureDateStr != null && !manufactureDateStr.trim().isEmpty()) {
                    product.setProductManufactureDate(dateFormat.parse(manufactureDateStr));
                }
                if (expiryDateStr != null && !expiryDateStr.trim().isEmpty()) {
                    product.setProductExpiryDate(dateFormat.parse(expiryDateStr));
                }
            } catch (ParseException e) {
                throw new ServletException("Invalid date format", e);
            }

            // Handle image update if provided
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                
                filePart.write(uploadPath + File.separator + fileName);
                product.setProductImage(UPLOAD_DIRECTORY + "/" + fileName);
            }

            // Validate and save
            String validationError = productService.validateProductInput(product);
            if (validationError != null) {
                request.setAttribute("error", validationError);
                request.setAttribute("selectedProduct", product);
                request.getRequestDispatcher("/WEB-INF/pages/updateproducts.jsp").forward(request, response);
                return;
            }

            if (productService.updateProduct(product)) {
                response.sendRedirect(request.getContextPath() + "/dashboard.do");
            } else {
                request.setAttribute("error", "Failed to update product");
                request.setAttribute("selectedProduct", product);
                request.getRequestDispatcher("/WEB-INF/pages/updateproducts.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID or price format");
            request.getRequestDispatcher("/WEB-INF/pages/updateproducts.jsp").forward(request, response);
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1)
                             .substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
} 