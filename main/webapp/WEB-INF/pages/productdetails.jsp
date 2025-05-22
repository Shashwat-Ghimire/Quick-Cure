<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.productName} | Quick Cure</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/productdetails.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="header.jsp" %>

    <!-- Product Details Section -->
    <section class="product-details container">
        <div class="product-details-content animate__animated animate__fadeIn">
            <div class="product-image">
                <img src="${pageContext.request.contextPath}/images/${product.productImage}" alt="${product.productName}">
                <c:if test="${product.productStockStatus == 'low'}">
                    <span class="stock-badge low">Low Stock</span>
                </c:if>
            </div>
            <div class="product-info">
                <h1 class="product-name">${product.productName}</h1>
                <p class="product-type">${product.productType}</p>
                <p class="product-manufacturer">By ${product.productManufacturer}</p>
                <div class="product-price">$${product.productPrice}</div>
                <div class="product-description">
                    <h2>Description</h2>
                    <p>${product.productDescription}</p>
                </div>
                <c:if test="${user.role != 'admin'}">
                    <div class="product-actions">
                        <button class="btn-add-cart" data-product-id="${product.productId}">
                            <i class="fas fa-shopping-cart"></i> Add to Cart
                        </button>
                    </div>
                </c:if>
            </div>
        </div>
    </section>

    <!-- Toast Notification -->
    <div class="toast-notification" id="toast-notification">
        Product added to cart!
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
        document.addEventListener('DOMContentLoaded', function() {
            const addToCartButton = document.querySelector('.btn-add-cart');
            if (addToCartButton) {
                addToCartButton.addEventListener('click', function(e) {
                    e.preventDefault();
                    const productId = this.getAttribute('data-product-id');

                    // Send AJAX request to add product to cart
                    fetch('${pageContext.request.contextPath}/cart', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: 'action=add&productId=' + productId
                    })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            showToast('Product added to cart successfully!');
                            // Add animation to button
                            this.classList.add('clicked');
                            setTimeout(() => {
                                this.classList.remove('clicked');
                            }, 300);
                        } else {
                            showToast('Failed to add product to cart: ' + data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        showToast('An error occurred while adding the product to cart.');
                    });
                });
            }
        });
    </script>
</body>
</html> 