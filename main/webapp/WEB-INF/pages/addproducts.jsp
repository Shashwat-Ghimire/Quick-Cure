<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        /* Add Product Form Styles */
        .add-product-container {
            max-width: 600px;
            margin: 40px auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .add-product-container h2 {
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

        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            color: #333;
            transition: border-color 0.3s ease, transform 0.2s ease;
        }

        .form-group input:focus,
        .form-group textarea:focus,
        .form-group select:focus {
            border-color: #0059b3;
            outline: none;
            transform: scale(1.02);
        }

        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-group input[type="file"] {
            padding: 3px;
        }

        .form-group input:hover,
        .form-group textarea:hover,
        .form-group select:hover {
            transform: scale(1.01);
        }

        .submit-btn {
            display: block;
            width: 100%;
            padding: 12px;
            background-color: #0059b3;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .submit-btn:hover {
            background-color: #003f7f;
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
            .add-product-container {
                margin: 20px;
                padding: 20px;
            }

            .add-product-container h2 {
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
        <div class="add-product-container">
            <a href="${pageContext.request.contextPath}/dashboard.do" class="back-btn"><i class="fa fa-arrow-left"></i> Back to Dashboard</a>
            <h2>Add New Product</h2>
            
            <!-- Toast Notification -->
            <div id="toast" style="display: none; position: fixed; top: 20px; right: 20px; background-color: #4CAF50; color: white; padding: 15px; border-radius: 4px; z-index: 1000;">${message}</div>
            
            <form action="${pageContext.request.contextPath}/addproducts.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="productName">Product Name</label>
                    <input type="text" id="productName" name="productName" placeholder="Enter product name" required>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" placeholder="Enter product description" required></textarea>
                </div>
                <div class="form-group">
                    <label for="manufactureDate">Manufacture Date</label>
                    <input type="date" id="manufactureDate" name="manufactureDate" required>
                </div>
                <div class="form-group">
                    <label for="expiryDate">Expiry Date</label>
                    <input type="date" id="expiryDate" name="expiryDate" required>
                </div>
                <div class="form-group">
                    <label for="price">Price ($)</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" placeholder="Enter price" required>
                </div>
                <div class="form-group">
                    <label for="manufacturer">Manufacturer</label>
                    <input type="text" id="manufacturer" name="manufacturer" placeholder="Enter manufacturer name" required>
                </div>
                <div class="form-group">
                    <label for="image">Product Image</label>
                    <input type="file" id="image" name="image" accept="image/*" required>
                </div>
                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" name="category" required>
                        <option value="" disabled selected>Select category</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category}">${category}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="submit-btn">Add Product</button>
            </form>
        </div>
    </main>

    <!-- Toast Script -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const toast = document.getElementById('toast');
            const message = '${message}';
            if (message && message.trim() !== '') {
                toast.textContent = message;
                toast.style.display = 'block';
                setTimeout(() => {
                    toast.style.display = 'none';
                }, 3000);
            }
        });
    </script>

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