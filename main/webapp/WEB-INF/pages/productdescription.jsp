<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details | Quick Cure</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productdescription.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="header.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <section class="product-details animate__animated animate__fadeIn">
            <c:choose>
                <c:when test="${not empty product}">
                    <div class="product-container">
                        <!-- Product Image -->
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}${product.image}" alt="${product.name}" class="main-image">
                        </div>

                        <!-- Product Information -->
                        <div class="product-info">
                            <h1 class="product-name">${product.name}</h1>
                            <p class="product-price">$<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/></p>
                            <p class="product-category"><strong>Category:</strong> ${product.category}</p>
                            <p class="product-description">${product.description}</p>
                            <div class="product-dates">
                                <p><strong>Manufacture Date:</strong> <fmt:formatDate value="${product.manufactureDate}" pattern="MMM dd, yyyy"/></p>
                                <p><strong>Expiry Date:</strong> <fmt:formatDate value="${product.expiryDate}" pattern="MMM dd, yyyy"/></p>
                            </div>
                            <div class="product-actions">
                                <button class="product-button" data-id="${product.id}"><i class="fas fa-cart-plus"></i> Add to Cart</button>
                                <a href="${pageContext.request.contextPath}/products" class="back-to-products"><i class="fas fa-arrow-left"></i> Back to Products</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="error-message">
                        <p>Product not found. Please try again later.</p>
                        <a href="${pageContext.request.contextPath}/products" class="back-to-products"><i class="fas fa-arrow-left"></i> Back to Products</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>

    <!-- Toast Notification -->
    <div class="toast-notification" id="toast-notification">
        Product added to cart!
    </div>

    <!-- Scroll to Top Button -->
    <div class="scroll-top" id="scroll-top">
        <i class="fas fa-arrow-up"></i>
    </div>

    <!-- Include Footer -->
    <%@ include file="footer.jsp" %>

    <script>
        // Function to show toast notification
        function showToast(message) {
            const toast = document.getElementById('toast-notification');
            toast.textContent = message;
            toast.classList.add('show');
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000);
        }

        // Add to cart functionality
        document.querySelectorAll('.product-button').forEach(button => {
            button.addEventListener('click', (e) => {
                const productId = button.dataset.id;
                showToast('Product added to cart!');
                button.classList.add('clicked');
                setTimeout(() => {
                    button.classList.remove('clicked');
                }, 300);
            });
        });

        // Scroll to top functionality
        window.addEventListener('scroll', () => {
            const scrollTopButton = document.getElementById('scroll-top');
            if (window.pageYOffset > 300) {
                scrollTopButton.classList.add('show');
            } else {
                scrollTopButton.classList.remove('show');
            }
        });

        document.getElementById('scroll-top').addEventListener('click', () => {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });

        // Initialize page with animation
        document.addEventListener('DOMContentLoaded', () => {
            const productContainer = document.querySelector('.product-container');
            if (productContainer) {
                productContainer.classList.add('animate__animated', 'animate__fadeInUp');
            }
        });
    </script>
</body>
</html>