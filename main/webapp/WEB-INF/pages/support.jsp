<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Support</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/support.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <div class="support-container">
            <a href="${pageContext.request.contextPath}/home.do" class="back-btn"><i class="fa fa-arrow-left"></i> Back to Home</a>
            <h2>Contact Support</h2>
            <form id="supportForm" action="${pageContext.request.contextPath}/support.do" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="Enter your username" required>
                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" placeholder="Enter your address" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" placeholder="Enter your phone number" pattern="[0-9]{10}" required>
                </div>
                <div class="form-group">
                    <label for="message">Questions or Feedback</label>
                    <textarea id="message" name="message" placeholder="Write your questions or feedback here" required></textarea>
                </div>
                <button type="submit" class="submit-btn"><i class="fa fa-paper-plane"></i> Submit</button>
            </form>
        </div>

        <!-- Toast Notification -->
        <div class="toast" id="toast">
            <i class="fa fa-check-circle"></i>
            <span>Your submission has been sent successfully!</span>
        </div>
    </main>

    <!-- JavaScript for Form Submission and Toast -->
    <script>
        document.getElementById('supportForm').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent actual form submission for front-end demo
            showToast();
            this.reset(); // Reset form after submission
        });

        function showToast() {
            const toast = document.getElementById('toast');
            toast.classList.add('show');
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000); // Hide after 3 seconds
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