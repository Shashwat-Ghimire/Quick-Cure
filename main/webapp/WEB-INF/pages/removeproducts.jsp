<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Remove Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        /* Remove Product Form Styles */
        .remove-product-container {
            max-width: 600px;
            margin: 40px auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .remove-product-container h2 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            color: #333;
            margin-bottom: 8px;
            font-weight: bold;
        }

        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            color: #333;
            transition: border-color 0.3s ease, transform 0.2s ease;
        }

        .form-group select:focus {
            border-color: #0059b3;
            outline: none;
            transform: scale(1.02);
        }

        .form-group select:hover {
            transform: scale(1.01);
        }

        .submit-btn {
            display: block;
            width: 100%;
            padding: 12px;
            background-color: #b30000;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .submit-btn:hover {
            background-color: #7f0000;
            transform: translateY(-2px);
        }

        .back-btn {
            display: inline-block;
            margin-bottom: 20px;
            color: #0059b3;
            text-decoration: none;
            font-size: 14px;
        }

        .back-btn i {
            margin-right: 5px;
        }

        .back-btn:hover {
            text-decoration: underline;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .remove-product-container {
                margin: 20px;
                padding: 20px;
            }

            .remove-product-container h2 {
                font-size: 20px;
            }

            .submit-btn {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <div class="remove-product-container">
            <a href="${pageContext.request.contextPath}/dashboard.do" class="back-btn"><i class="fa fa-arrow-left"></i> Back to Dashboard</a>
            <h2>Remove Product</h2>
            <form action="${pageContext.request.contextPath}/removeproducts.do" method="post">
                <input type="hidden" name="action" value="remove">
                <div class="form-group">
                    <label for="productId">Select Product</label>
                    <select id="productId" name="productId" required>
                        <option value="" disabled selected>Select a product to remove</option>
                        <c:forEach var="product" items="${productList}">
                            <option value="${product.productId}">${product.productName}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="submit-btn">Remove Product</button>
            </form>
        </div>
    </main>

    <!-- Mobile Navigation -->
    <nav class="mobile-nav">
        <a href="#" class="nav-item">Dashboard</a>
        <a href="#" class="nav-item">Orders</a>
        <a href="#" class="nav-item">Products</a>
        <a href="#" class="nav-item">Customers</a>
        <a href="#" class="nav-item">Settings</a>
    </nav>
</body>
</html>