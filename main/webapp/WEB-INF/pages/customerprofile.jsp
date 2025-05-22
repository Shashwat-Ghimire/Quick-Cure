<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customerprofile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <div class="customer-profile-container">
            <a href="${pageContext.request.contextPath}/home.do" class="back-btn"><i class="fa fa-arrow-left"></i> Back to Home</a>
            <h2>Customer Profile</h2>
            <form action="${pageContext.request.contextPath}/customerprofile.do" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value="<c:out value='${customer.firstName}'/>" placeholder="Enter your first name" required>
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value="<c:out value='${customer.lastName}'/>" placeholder="Enter your last name" required>
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="<c:out value='${customer.address}'/>" placeholder="Enter your address" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" value="<c:out value='${customer.phoneNumber}'/>" placeholder="Enter your phone number" pattern="[0-9]{10}" required>
                </div>
                <div class="form-group">
                    <label for="image">Profile Image</label>
                    <div class="profile-image-preview">
                        <img src="<c:out value='${customer.image != null ? customer.image : pageContext.request.contextPath}/images/default-profile.png'/>" alt="Profile Preview" id="imagePreview">
                        <input type="file" id="image" name="image" accept="image/*" onchange="previewImage(event)">
                    </div>
                </div>
                <button type="submit" class="submit-btn"><i class="fa fa-save"></i> Save Profile</button>
            </form>
        </div>
    </main>

    <!-- JavaScript for Image Preview -->
    <script>
        function previewImage(event) {
            const reader = new FileReader();
            reader.onload = function() {
                const output = document.getElementById('imagePreview');
                output.src = reader.result;
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    </script>

    <!-- Mobile Navigation -->
    <nav class="mobile-nav">
        <a href="#" class="nav-item">Home</a>
        <a href="#" class="nav-item">Products</a>
        <a href="#" class="nav-item">Cart</a>
        <a href="#" class="nav-item">Profile</a>
        <a href="#" class="nav-item">Support</a>
    </nav>
</body>
</html>