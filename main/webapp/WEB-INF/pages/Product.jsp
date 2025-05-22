<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Products | Quick Cure</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">

</head>

<body>

    <!-- Include Header -->

    <%@ include file="header.jsp" %>



    <!-- Banner with Search Bar -->

    <section class="banner">

        <img src="${pageContext.request.contextPath}/images/back.jpg" alt="Banner Background" class="banner-background">

        <div class="banner-content animate__animated animate__fadeInUp">

            <h1>Welcome to Quick Cure</h1>

            <p>Your trusted source for medicines and medical tools</p>

            <form action="${pageContext.request.contextPath}/products" method="get" class="search-bar">

                <input type="text" name="search" id="search-input" placeholder="Search products..." value="${searchQuery}">

                <button type="submit" class="search-button"><i class="fas fa-search"></i></button>

            </form>

        </div>

    </section>



    <!-- Main Content Wrapper -->

    <div class="main-content">

        <!-- Products Section -->

        <section class="products">

            <div class="page-header">

                <h2>Our Products</h2>

            </div>



            <!-- Product Grid -->

            <div class="products-layout">

                <div class="product-grid" id="product-grid">

                    <c:if test="${empty products}">

                        <div class="no-products">

                            <p>No products found matching your criteria.</p>

                        </div>

                    </c:if>

                    

                    <c:forEach var="product" items="${products}">

                        <div class="product-card" data-id="${product.productId}">

                            <div class="product-image">

                                <img src="${pageContext.request.contextPath}/images/${product.productImage}" alt="${product.productName}">

                                <c:if test="${product.productStockStatus == 'low'}">

                                    <span class="stock-badge low">Low Stock</span>

                                </c:if>

                            </div>

                            <div class="product-info">

                                <h3>${product.productName}</h3>

                                <p class="product-type">${product.productType}</p>

                                <p class="product-description">${product.productDescription}</p>

                                <p class="product-manufacturer">By ${product.productManufacturer}</p>

                                <div class="product-footer">

                                    <span class="product-price">$${product.productPrice}</span>

                                    <div class="product-actions">

                                        <a href="${pageContext.request.contextPath}/product/${product.productId}" class="btn-details">View Details</a>

                                        <c:if test="${sessionScope.userRole != 'admin'}">

                                            <button class="product-button btn-add-cart" onclick="addToCart(${product.productId})">Add to Cart</button>

                                        </c:if>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </c:forEach>

                </div>



                <!-- Pagination -->

                <div class="pagination">

                    <c:if test="${currentPage > 1}">

                        <a href="?page=${currentPage - 1}${not empty param.search ? '&search='.concat(param.search) : ''}" class="page-link">&laquo; Previous</a>

                    </c:if>

                    

                    <c:forEach begin="1" end="${totalPages}" var="i">

                        <c:choose>

                            <c:when test="${currentPage == i}">

                                <span class="page-link active">${i}</span>

                            </c:when>

                            <c:otherwise>

                                <a href="?page=${i}${not empty param.search ? '&search='.concat(param.search) : ''}" class="page-link">${i}</a>

                            </c:otherwise>

                        </c:choose>

                    </c:forEach>

                    

                    <c:if test="${currentPage < totalPages}">

                        <a href="?page=${currentPage + 1}${not empty param.search ? '&search='.concat(param.search) : ''}" class="page-link">Next &raquo;</a>

                    </c:if>

                </div>

            </div>

        </section>

    </div>



    <!-- Scroll to Top Button -->

    <div class="scroll-top" id="scroll-top">

        <i class="fas fa-arrow-up"></i>

    </div>



    <!-- Toast Notification -->

    <div id="toast-notification" style="display: none; position: fixed; top: 20px; right: 20px; background-color: #4CAF50; color: white; padding: 15px; border-radius: 4px; z-index: 1000;"></div>



    <!-- Include Footer -->

    <%@ include file="footer.jsp" %>



    <!-- Font Awesome for icons -->

    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>



    <script>

        // Function to show toast notification

        function showToast(message) {

            const toast = document.getElementById('toast-notification');

            toast.textContent = message;

            toast.style.display = 'block';

            setTimeout(() => {

                toast.style.display = 'none';

            }, 3000);

        }



        // Add to cart functionality

        function addToCart(productId) {

            fetch('${pageContext.request.contextPath}/cart.do', {

                method: 'POST',

                headers: {

                    'Content-Type': 'application/x-www-form-urlencoded',

                },

                body: 'action=add&productId=' + productId + '&quantity=1'

            })

            .then(response => {

                if (!response.ok) {

                    throw new Error('Network response was not ok');

                }

                return response.json();

            })

            .then(data => {

                if (data.success) {

                    showToast(data.message);

                } else {

                    showToast(data.message || 'Error adding product to cart');

                }

            })

            .catch(error => {

                console.error('Error:', error);

                showToast('Error adding product to cart');

            });

        }



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

    </script>

</body>

</html>