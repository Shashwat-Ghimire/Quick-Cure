<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
/* Sidebar */
.sidebar-checkbox {
    display: none;
}

.sidebar {
    background-color: #0059b3;
    width: 250px;
    position: fixed;
    top: 0;
    bottom: 0;
    padding: 20px;
    z-index: 1000;
    transition: width 0.3s ease;
    overflow: hidden;
}

.logo-container {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 30px;
}

.logo {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    object-fit: cover;
}

.company-name {
    font-size: 20px;
    font-weight: bold;
    color: #fff;
    transition: opacity 0.3s ease;
}

.toggle-btn {
    font-size: 20px;
    color: #fff;
    cursor: pointer;
    margin-left: auto;
    transition: transform 0.3s ease;
}

.toggle-btn:hover {
    transform: scale(1.1);
}

.sidebar ul {
    list-style: none;
}

.sidebar li {
    margin-bottom: 10px;
}

.sidebar a {
    display: flex;
    align-items: center;
    gap: 15px;
    color: #fff;
    text-decoration: none;
    padding: 10px;
    border-radius: 4px;
    transition: background-color 0.3s ease, transform 0.2s ease;
}

.sidebar a i {
    font-size: 18px;
    width: 20px;
    text-align: center;
}

.sidebar a span {
    font-size: 16px;
    transition: opacity 0.3s ease;
}

.sidebar a:hover {
    background-color: #003f7f;
    transform: translateX(5px);
}

/* Collapsed Sidebar */
.sidebar-checkbox:checked ~ .sidebar {
    width: 70px;
}

.sidebar-checkbox:checked ~ .sidebar .company-name,
.sidebar-checkbox:checked ~ .sidebar a span {
    opacity: 0;
    pointer-events: none;
}

.sidebar-checkbox:checked ~ .sidebar .logo {
    width: 30px;
    height: 30px;
}

.sidebar-checkbox:checked ~ .main-content {
    margin-left: 70px;
}
</style>
</head>
<body>
<!-- Sidebar -->
<input type="checkbox" id="sidebar-toggle" class="sidebar-checkbox">
<aside class="sidebar">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/images/logo.jpg" alt="Quick Cure Logo" class="logo">
        <span class="company-name">Quick Cure</span>
        <label for="sidebar-toggle" class="toggle-btn"><i class="fa fa-bars"></i></label>
    </div>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/dashboard.do">
                <i class="fa fa-line-chart"></i>
                <span>Dashboard</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/orders.do">
                <i class="fa fa-shopping-cart"></i>
                <span>Orders</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/product.do">
                <i class="fa fa-cube"></i>
                <span>Products</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/support.do">
                <i class="fa fa-question"></i>
                <span>Support</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/customerprofile.do">
                <i class="fa fa-user"></i>
                <span>Profile</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/home.do">
                <i class="fa fa-home"></i>
                <span>Home</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/login.do">
                <i class="fa fa-sign-out"></i>
                <span>Sign Out</span>
            </a>
        </li>
    </ul>
</aside>
</body>
</html>