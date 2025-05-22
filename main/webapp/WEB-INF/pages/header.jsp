<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>header</title>
    <!-- Font Awesome 4.7.0 CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
        }

        .header {
            background-color: #0059b3;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 15px 30px;
            position: sticky;
            width: 100%;
            top: 0;
            z-index: 1000;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .logo-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .logo {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
        }

        .company-name {
            font-size: 24px;
            font-weight: bold;
            color: #fff;
        }

        .icons {
            display: flex;
            gap: 50px;
            align-items: center;
        }

        .icon-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            text-decoration: none;
            color: #fff;
            transition: color 0.3s ease;
        }

        .icon-container i {
            font-size: 24px;
            margin-bottom: 5px;
        }

        .icon-container span {
            font-size: 12px;
            font-weight: 500;
        }

        .icon-container:hover i,
        .icon-container:hover span {
            color: #b3ffff;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 15px;
                padding: 15px;
            }

            .icons {
                gap: 15px;
                flex-wrap: wrap;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/images/logo.jpg" alt="Logo" class="logo">
            <span class="company-name">Quick Cure</span>
        </div>
        
        <div class="icons">
            <a href="${pageContext.request.contextPath}/home.do" class="icon-container">
                <i class="fa fa-home"></i>
                <span>Home</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/product.do" class="icon-container">
                <i class="fa fa-shopping-bag"></i>
                <span>Products</span>
            </a>

            <a href="${pageContext.request.contextPath}/support.do" class="icon-container">
                <i class="fa fa-question"></i>
                <span>Support</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/customerprofile.do" class="icon-container">
                <i class="fa fa-user-circle"></i>
                <span>Profile</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/cart.do" class="icon-container">
                <i class="fa fa-shopping-cart"></i>
                <span>Cart</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/Userdash.do" class="icon-container">
                <i class="fa fa-line-chart"></i>
                <span>Dashboard</span>
            </a>
            <a href="${pageContext.request.contextPath}/login.do" class="icon-container">
                <i class="fa fa-sign-out"></i>
                <span>Sign out</span>
            </a>
        </div>
    </header>
</body>
</html>