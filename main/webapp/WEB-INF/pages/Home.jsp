<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quick Cure - Home</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>

	<%@ include file="header.jsp" %>
    <!-- First Banner -->
    <div class="banner">
        <img src="${pageContext.request.contextPath}/images/register.jpg" alt="Medical Banner">
        <div class="search-container">
            <h1>Welcome to Quick Cure</h1>
            <form>
                <input type="text" placeholder="Search...">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>
    </div>

    <!-- Second Banner -->
    <div class="overview">
        <h2>About Quick Cure</h2>
        <p>Quick Cure is your one-stop online pharmacy for all your medical needs. We offer a wide range of medicines and healthcare products delivered right to your doorstep.</p>
        <a href="${pageContext.request.contextPath}/product.do"><button class="overview-btn">Explore Products</button></a>
    </div>

    <!-- Third Banner (Features Section) -->
    <div class="banner">
        <img src="${pageContext.request.contextPath}/images/bannerp.jpg" alt="Medical Banner">
        <div class="search-container">
            <h1 style="text-align: left !important;">Quick Cure Website Features</h1>
           
            <div class="features-list" style="text-align: left !important; line-height: 1.6 !important; font-weight: bold; ">
                Login/Register: Secure authentication for user accounts.<br>
                Products: Catalog of medicines and tools with search and filter options.<br>
                Orders: View and track order history.<br>
                Purchases: Manage past and current transactions.<br>
                My Cart: Temporary storage for items before checkout.<br>
                Payment: Secure payment processing for purchases.<br>
                Dashboard: Centralized overview of user activities.<br>
                Profile: Manage personal information and settings.<br>
                User-Friendly Design: Intuitive, responsive interface for easy navigation.
    		</div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>