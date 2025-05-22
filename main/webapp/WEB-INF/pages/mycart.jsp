<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Cart | Quick Cure</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mycart.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="header.jsp" %>

    <!-- Cart Section -->
    <section class="cart container">
        <h1 class="cart-title animate__animated animate__fadeInDown">My Cart</h1>
        <div class="cart-content">
            <!-- Cart Items -->
            <div class="cart-items">
                <c:choose>
                    <c:when test="${empty cartItems}">
                        <div class="empty-cart animate__animated animate__fadeIn">
                            <p>Your cart is empty. <a href="${pageContext.request.contextPath}/products.do">Start shopping now!</a></p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${cartItems}">
                            <div class="cart-item animate__animated animate__fadeInUp" data-id="${item.productId}">
                                <div class="cart-item-image">
                                    <img src="${pageContext.request.contextPath}/images/${item.productImage}" alt="${item.productName}">
                                </div>
                                <div class="cart-item-details">
                                    <h3>${item.productName}</h3>
                                    <p class="price">$${item.productPrice}</p>
                                    <div class="quantity-control">
                                        <button class="quantity-btn subtract" aria-label="Decrease quantity">-</button>
                                        <span class="quantity-value">1</span>
                                        <button class="quantity-btn add" aria-label="Increase quantity">+</button>
                                    </div>
                                </div>
                                <div class="cart-item-actions">
                                    <button class="remove-btn" aria-label="Remove item" data-product-id="${item.productId}">
                                        <i class="fas fa-trash-alt"></i> Remove
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Cart Summary -->
            <c:if test="${not empty cartItems}">
                <div class="cart-summary animate__animated animate__fadeInRight">
                    <h2>Order Summary</h2>
                    <div class="summary-details">
                        <p>Subtotal: <span id="subtotal">$${cartTotal}</span></p>
                        <p>Shipping: <span>Free</span></p>
                        <p class="total">Total: <span id="total">$${cartTotal}</span></p>
                    </div>
                    <form action="${pageContext.request.contextPath}/PaymentController" method="post" id="checkout-form">
                        <input type="hidden" name="orderAmount" value="${cartTotal}">
                        <input type="hidden" name="action" value="checkout">
                        <button type="submit" class="checkout-btn">Proceed to Checkout</button>
                    </form>
                </div>
            </c:if>
        </div>
    </section>

    <!-- Scroll to Top Button -->
    <div class="scroll-top" id="scroll-top">
        <i class="fas fa-arrow-up"></i>
    </div>

    <!-- Toast Notification -->
    <div class="toast-notification" id="toast-notification">
        Item removed from cart!
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

        // Calculate and update cart totals
        function updateCartTotals() {
            let subtotal = 0;
            document.querySelectorAll('.cart-item').forEach(item => {
                const price = parseFloat(item.querySelector('.price').textContent.replace('$', ''));
                const quantity = parseInt(item.querySelector('.quantity-value').textContent);
                subtotal += price * quantity;
            });
            document.getElementById('subtotal').textContent = `$${subtotal.toFixed(2)}`;
            document.getElementById('total').textContent = `$${subtotal.toFixed(2)}`; // Shipping is free
        }

        // Quantity controls
        document.querySelectorAll('.cart-item .add').forEach(button => {
            button.addEventListener('click', () => {
                const cartItem = button.closest('.cart-item');
                const productId = cartItem.dataset.id;
                const quantitySpan = button.parentElement.querySelector('.quantity-value');
                const newQuantity = parseInt(quantitySpan.textContent) + 1;
                
                // Update quantity on server
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `action=update&productId=${productId}&quantity=${newQuantity}`
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        quantitySpan.textContent = newQuantity;
                        updateCartTotals();
                    } else {
                        showToast(data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showToast('An error occurred while updating quantity');
                });
            });
        });

        document.querySelectorAll('.cart-item .subtract').forEach(button => {
            button.addEventListener('click', () => {
                const cartItem = button.closest('.cart-item');
                const productId = cartItem.dataset.id;
                const quantitySpan = button.parentElement.querySelector('.quantity-value');
                const currentQuantity = parseInt(quantitySpan.textContent);
                
                if (currentQuantity > 1) {
                    const newQuantity = currentQuantity - 1;
                    
                    // Update quantity on server
                    fetch('${pageContext.request.contextPath}/cart', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: `action=update&productId=${productId}&quantity=${newQuantity}`
                    })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            quantitySpan.textContent = newQuantity;
                            updateCartTotals();
                        } else {
                            showToast(data.message);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        showToast('An error occurred while updating quantity');
                    });
                }
            });
        });

        // Remove item functionality
        document.querySelectorAll('.cart-item .remove-btn').forEach(button => {
            button.addEventListener('click', () => {
                const cartItem = button.closest('.cart-item');
                const productId = button.dataset.productId;
                
                // Send request to remove item
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `action=remove&productId=${productId}`
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        cartItem.classList.add('animate__animated', 'animate__fadeOut');
                        cartItem.addEventListener('animationend', () => {
                            cartItem.remove();
                            updateCartTotals();
                            showToast('Item removed from cart!');
                            if (!document.querySelector('.cart-item')) {
                                location.reload(); // Reload to show empty cart message
                            }
                        });
                    } else {
                        showToast(data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showToast('An error occurred while removing the item');
                });
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

        // Initialize the page
        document.addEventListener('DOMContentLoaded', () => {
            updateCartTotals();
        });
    </script>
</body>
</html>