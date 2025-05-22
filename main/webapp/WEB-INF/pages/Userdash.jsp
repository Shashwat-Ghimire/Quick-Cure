<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>User Dashboard</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user.css">

    <script src='https://kit.fontawesome.com/a076d05399.js'></script>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>

    

<input type="checkbox" id="sidebar-toggle" class="sidebar-checkbox">

<aside class="sidebar">

    <div class="logo-container">

        <img src="https://via.placeholder.com/40" alt="Quick Cure Logo" class="logo">

        <span class="company-name">Quick Cure</span>

        <label for="sidebar-toggle" class="toggle-btn"><i class="fa fa-bars"></i></label>

    </div>

    <ul>

        <li>

            <a href="${pageContext.request.contextPath}/Userdash.do">

                <i class="fa fa-line-chart"></i>

                <span>Dashboard</span>

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



    <!-- Main Content -->

    <main class="main-content">

        <!-- Header -->

        <header class="header">

            <div class="search-bar">

                <input type="text" placeholder="Search...">

                <button class="search-btn"><i class="fa fa-search"></i></button>

            </div>

            <div class="user-menu">

                <i class="fa fa-user-circle user-icon" onclick="toggleUserMenu()"></i>

                <div class="user-dropdown" id="userDropdown">

                    <p class="user-info">Username: ${userName}</p>

                    <p class="user-info">Email: ${userEmail}</p>

                    <a href="${pageContext.request.contextPath}/customerprofile.do" class="dropdown-item">Settings</a>

                    <a href="${pageContext.request.contextPath}/login.do" class="dropdown-item">Sign Out</a>

                </div>

            </div>

        </header>



        <!-- Dashboard Content -->

        <section class="dashboard-content">

            <h1>Welcome, ${userName}!</h1>

            <p>Your healthcare dashboard is up to date</p>



            <!-- Stats -->

            <div class="user-stats">

                <div class="stat-card">

                    <h3>Total Orders</h3>

                    <p>${userDashData.totalOrders}</p>

                    <span class="change positive">Completed: ${userDashData.completedOrders}</span>

                </div>

                <div class="stat-card">

                    <h3>Total Spent</h3>

                    <p>$<fmt:formatNumber value="${userDashData.totalSpent}" pattern="#,##0.00"/></p>

                </div>

            </div>



            <!-- Recent Orders -->

            <section class="recent-orders">

                <div class="section-header">

                    <h2>Recent Orders</h2>

                    <a href="${pageContext.request.contextPath}/orders.do" class="view-all">View All</a>

                </div>

                <table>

                    <thead>

                        <tr>

                            <th>Order ID</th>

                            <th>Date</th>

                            <th>Total</th>

                            <th>Status</th>

                            <th></th>

                        </tr>

                    </thead>

                    <tbody>

                        <c:forEach var="order" items="${recentOrders}">

                            <tr>

                                <td><c:out value="${order.orderId}"/></td>

                                <td><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy"/></td>

                                <td>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></td>

                                <td><span class="status ${order.status.toLowerCase()}">${order.status}</span></td>

                                <td><a href="${pageContext.request.contextPath}/order/details.do?id=${order.orderId}" class="details-btn">🔍</a></td>

                            </tr>

                        </c:forEach>

                        <c:if test="${empty recentOrders}">

                            <tr>

                                <td colspan="5" class="no-orders">No recent orders found.</td>

                            </tr>

                        </c:if>

                    </tbody>

                </table>

            </section>



            <!-- Recent Purchases -->

            <section class="purchases">

                <div class="section-header">

                    <h2>Recent Orders</h2>

                    <a href="${pageContext.request.contextPath}/orders.do" class="view-all">View All</a>

                </div>

                <c:forEach var="order" items="${recentOrders}">

                    <div class="purchase-card">

                        <span class="purchase-icon">📦</span>

                        <div class="purchase-details">

                            <h3>Order #${order.orderId}</h3>

                            <p><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy"/></p>

                        </div>

                        <div class="purchase-status">

                            <p>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></p>

                            <span class="status ${order.status.toLowerCase()}">${order.status}</span>

                        </div>

                    </div>

                </c:forEach>

                <c:if test="${empty recentOrders}">

                    <div class="purchase-card">

                        <p class="no-orders">No recent orders found.</p>

                    </div>

                </c:if>

            </section>

        </section>

    </main>

	<script>

    function toggleUserMenu() {

        const dropdown = document.getElementById('userDropdown');

        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';

    }



    // Close the dropdown if clicking outside

    window.onclick = function(event) {

        const dropdown = document.getElementById('userDropdown');

        const userIcon = document.querySelector('.user-icon');

        if (!userIcon.contains(event.target) && !dropdown.contains(event.target)) {

            dropdown.style.display = 'none';

        }

    }

</script>

    <!-- Mobile Navigation -->

    <nav class="mobile-nav">

        <a href="#" class="nav-item">Dashboard</a>

        <a href="#" class="nav-item">Purchases</a>

        <a href="#" class="nav-item">Profile</a>

        <a href="#" class="nav-item">Settings</a>

    </nav>

</body>

</html>