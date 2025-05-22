<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quick Cure - Orders</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="sidebar.jsp" %>

<!-- Main Content -->
<main class="main-content">
    <!-- Orders List -->
    <section class="orders-list">
        <div class="section-header">
            <h2>Your Orders</h2>
        </div>
        <c:if test="${empty orders}">
            <p>No orders found.</p>
        </c:if>
        <c:if test="${not empty orders}">
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td><c:out value="${order.orderId}"/></td>
                            <td><fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy"/></td>
                            <td>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></td>
                            <td><span class="status ${order.status.toLowerCase()}">${order.status}</span></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/order/view/${order.orderId}" class="btn-view">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </section>
</main>
</body>
</html>