package com.quickcure.controller;

import com.quickcure.model.Product;
import com.quickcure.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Servlet implementation class ProductController
 */
@WebServlet(urlPatterns = {"/product.do", "/products.do"})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());
    private static final int PRODUCTS_PER_PAGE = 8;
    private final ProductService productService;

    public ProductController() {
        super();
        this.productService = new ProductService();
    }

    /**
     * Handles GET requests for /products to display the product list.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get page number from request parameter
            int page = 1;
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                page = Integer.parseInt(pageStr);
            }

            // Get search query if exists
            String searchQuery = request.getParameter("search");
            List<Product> products;
            int totalProducts;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                products = productService.getProductsBySearch(searchQuery, page);
                totalProducts = productService.getTotalProductsBySearch(searchQuery);
            } else {
                products = productService.getProductsByPage(page);
                totalProducts = productService.getTotalProducts();
            }

            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);

            // Set attributes for JSP
            request.setAttribute("products", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);

            // Forward to product list page
            request.getRequestDispatcher("/WEB-INF/pages/Product.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving products", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving products");
        }
    }
}