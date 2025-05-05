<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="header.jsp" %>

    <!-- Products Section -->
    <section class="products container">
        <h2>Our Products</h2>
        <div class="products-layout">
            <!-- Filter Sidebar -->
            <aside class="filter-sidebar">
                <h3>Filters</h3>
                <!-- Category Filter -->
                <div class="filter-group">
                    <h4>Category</h4>
                    <label><input type="checkbox" name="category" value="pain-relief"> Pain Relief</label>
                    <label><input type="checkbox" name="category" value="vitamins"> Vitamins</label>
                    <label><input type="checkbox" name="category" value="cough-cold"> Cough and Cold</label>
                    <label><input type="checkbox" name="category" value="first-aid"> First Aid</label>
                </div>
                <!-- Price Range Filter -->
                <div class="filter-group">
                    <h4>Price Range</h4>
                    <label><input type="radio" name="price" value="0-10"> $0 - $10</label>
                    <label><input type="radio" name="price" value="10-20"> $10 - $20</label>
                    <label><input type="radio" name="price" value="20+"> $20+</label>
                </div>
                <button class="filter-button">Apply Filters</button>
            </aside>
            <!-- Product Grid -->
            <div class="product-grid">
                <!-- Product Card 1 -->
                <div class="product-card">
                    <img src="" alt="Product 1">
                    <div class="product-info">
                        <h3>Pain Relief Tablets</h3>
                        <p>$9.99</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
                <!-- Product Card 2 -->
                <div class="product-card">
                    <img src="" alt="Product 2">
                    <div class="product-info">
                        <h3>Vitamin C Supplements</h3>
                        <p>$12.99</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
                <!-- Product Card 3 -->
                <div class="product-card">
                    <img src="" alt="Product 3">
                    <div class="product-info">
                        <h3>Cough Syrup</h3>
                        <p>$7.99</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
                <!-- Product Card 4 -->
                <div class="product-card">
                    <img src="" alt="Product 4">
                    <div class="product-info">
                        <h3>Adhesive Bandages</h3>
                        <p>$4.99</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
                <!-- Product Card 5 -->
                <div class="product-card">
                    <img src="" alt="Product 5">
                    <div class="product-info">
                        <h3>Antibiotic Cream</h3>
                        <p>$15.99</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
                <!-- Product Card 6 -->
                <div class="product-card">
                    <img src="" alt="Product 6">
                    <div class="product-info">
                        <h3>Allergy Relief</h3>
                        <p>$11.49</p>
                        <a href="#" class="product-button">Add to Cart</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Include Footer -->
    <%@ include file="footer.jsp" %>
</body>
</html>