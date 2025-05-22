<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="sidebar.jsp" %>

<!-- Main Content -->
<main class="main-content">
    <!-- Header -->
<header class="header">
    <h3>Welcome</h3>
    <div class="user-menu">
        <div style="display: flex; align-items: center; gap: 5px;">
            <span style="font-size: 14px; color: #333;">User:</span>
            <i class="fa fa-user-circle user-icon" onclick="toggleUserMenu()"></i>
        </div>
        <div class="user-dropdown" id="userDropdown">
            <p class="user-info">Username: Admin</p>
            <p class="user-info">Email: admin@quickcure.com</p>
            <a href="${pageContext.request.contextPath}/login.do" class="dropdown-item">Sign Out</a>
        </div>
    </div>
</header>
    <section class="dashboard-content">
        <c:choose>
            <c:when test="${not empty dashboard}">
                <!-- Stats -->
                <div class="admin-stats">
                    <div class="stat-card">
                        <h3>Total Orders</h3>
                        <p><c:out value="${dashboard.totalOrders}"/></p>
                    </div>
                    <div class="stat-card">
                        <h3>Total Revenue</h3>
                        <p>$<fmt:formatNumber value="${dashboard.totalRevenue}" pattern="#,##0.00"/></p>
                    </div>
                    <div class="stat-card">
                        <h3>Active Customers</h3>
                        <p><c:out value="${dashboard.activeCustomers}"/></p>
                    </div>
                    <div class="stat-card">
                        <h3>Low Stock Items</h3>
                        <p><c:out value="${dashboard.lowStockItems}"/></p>
                    </div>
                </div>

                <!-- Main Sections -->
                <div class="main-sections">
                    <!-- Sales Overview -->
                    <section class="sales-overview">
                        <h2>Sales Overview</h2>
                        <p>Monthly sales performance</p>
                        <div class="graph-placeholder">
                            <canvas id="salesGraph" style="max-height: 300px;"></canvas>
                        </div>
                    </section>

                    <!-- Top Products -->
                    <section class="top-products">
                        <h2>Top Products</h2>
                        <p>Best performing products this month</p>
                        <c:forEach var="product" items="${dashboard.topProducts}">
                            <div class="product-item">
                                <span class="product-icon">💊</span>
                                <div class="product-details">
                                    <h3><c:out value="${product.name}" default="N/A"/></h3>
                                    <p>Category TBD</p>
                                </div>
                                <div class="product-stats">
                                    <p><c:out value="${product.sales}"/> sold</p>
                                    <span class="change positive">TBD%</span>
                                </div>
                            </div>
                        </c:forEach>
                    </section>
                </div>
                <div class="add-rm-btn" style="display: flex !important;">
                    <a href="${pageContext.request.contextPath}/addproducts.do" class="add-product-btn" style="margin-right: 10px;">
                        <i class="fa fa-plus"></i> Add Products
                    </a>
                    <a href="${pageContext.request.contextPath}/removeproducts.do" class="remove-product-btn" style="margin-right: 10px;">
                        <i class="fa fa-minus"></i> Remove Products
                    </a>
                    <a href="${pageContext.request.contextPath}/updateproducts.do" class="update-product-btn">
                        <i class="fa fa-wrench"></i> Update Products
                    </a>
                </div>
                <!-- Recent Orders -->
                <section class="recent-orders">
                    <div class="section-header">
                        <h2>Recent Orders</h2>
                        <a href="#" class="view-all">View All</a>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Customer</th>
                                <th>Date</th>
                                <th>Items</th>
                                <th>Total</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${dashboard.recentOrders}">
                                <tr>
                                    <td><c:out value="${order.id}"/></td>
                                    <td>Unknown</td>
                                    <td><c:out value="${order.orderDate != null ? order.orderDate : 'N/A'}"/></td>
                                    <td>TBD</td>
                                    <td>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></td>
                                    <td><span class="status processing">Processing</span></td>
                                    <td><a href="#" class="details-btn">🔍</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>
            </c:when>
            <c:otherwise>
                <div class="error-message">
                    <p>Unable to load dashboard data. Please try again later.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<!-- JavaScript to render the graph -->
<c:if test="${not empty dashboard}">
<script>
    const monthlySales = {
        labels: [<c:forEach var="entry" items="${dashboard.monthlySales}" varStatus="status">'${entry.key}'<c:if test="${!status.last}">,</c:if></c:forEach>],
        data: [<c:forEach var="entry" items="${dashboard.monthlySales}" varStatus="status">${entry.value}<c:if test="${!status.last}">,</c:if></c:forEach>]
    };

    const ctx = document.getElementById('salesGraph').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: monthlySales.labels,
            datasets: [{
                label: 'Sales ($)',
                data: monthlySales.data,
                borderColor: '#0059b3',
                backgroundColor: 'rgba(0, 89, 179, 0.2)',
                borderWidth: 2,
                fill: true,
                tension: 0.3
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Sales ($)'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Month'
                    }
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                },
                tooltip: {
                    enabled: true
                }
            }
        }
    });
</script>
</c:if>

<script>
    function toggleUserMenu() {
        const dropdown = document.getElementById('userDropdown');
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    }

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
    <a href="#" class="nav-item">Orders</a>
    <a href="#" class="nav-item">Products</a>
    <a href="#" class="nav-item">Customers</a>
    <a href="#" class="nav-item">Settings</a>
</nav>
</body>
</html>