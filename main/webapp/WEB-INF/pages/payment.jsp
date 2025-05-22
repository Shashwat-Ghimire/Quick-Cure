<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - Quick Cure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="payment-container">
            <h2>Payment Details</h2>
            
            <c:if test="${not empty error}">
                <div class="error-message">
                    <p>${error}</p>
                </div>
            </c:if>
            
            <div class="order-summary">
                <h3>Order Summary</h3>
                <c:if test="${not empty order && order.orderId > 0}">
                    <p><strong>Order ID:</strong> ${order.orderId}</p>
                </c:if>
                <p><strong>Total Amount:</strong> $${order.totalAmount}</p>
                <p><strong>Date:</strong> ${order.orderDate}</p>
                <p><strong>Status:</strong> ${order.status}</p>
                
                <c:if test="${not empty cartItems}">
                    <h4>Items in your order:</h4>
                    <ul class="cart-items-list">
                        <c:forEach var="item" items="${cartItems}">
                            <li>${item.productName} - $${item.productPrice} x ${item.quantity}</li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
            
            <form action="${pageContext.request.contextPath}/PaymentController" method="post" class="payment-form">
                <input type="hidden" name="amount" value="${order.totalAmount}">
                
                <div class="form-group">
                    <label for="paymentMethod">Payment Method</label>
                    <select id="paymentMethod" name="paymentMethod" required>
                        <option value="">Select Payment Method</option>
                        <option value="Credit Card">Credit Card</option>
                        <option value="Debit Card">Debit Card</option>
                        <option value="PayPal">PayPal</option>
                        <option value="Bank Transfer">Bank Transfer</option>
                    </select>
                </div>
                
                <div id="creditCardDetails" class="payment-details">
                    <div class="form-group">
                        <label for="cardNumber">Card Number</label>
                        <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" maxlength="19">
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group half">
                            <label for="expiryDate">Expiry Date</label>
                            <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" maxlength="5">
                        </div>
                        
                        <div class="form-group half">
                            <label for="cvv">CVV</label>
                            <input type="text" id="cvv" name="cvv" placeholder="123" maxlength="3">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="cardholderName">Cardholder Name</label>
                        <input type="text" id="cardholderName" name="cardholderName" placeholder="John Doe">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="billingAddress">Billing Address</label>
                    <textarea id="billingAddress" name="billingAddress" rows="3"></textarea>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn-primary">Complete Payment</button>
                    <a href="${pageContext.request.contextPath}/cart.do" class="btn-secondary">Back to Cart</a>
                </div>
            </form>
            
            <div class="secure-payment">
                <p><i class="fa fa-lock"></i> Your payment information is secure. We use encryption to protect your personal data.</p>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script>
        document.getElementById('paymentMethod').addEventListener('change', function() {
            var creditCardDetails = document.getElementById('creditCardDetails');
            if (this.value === 'Credit Card' || this.value === 'Debit Card') {
                creditCardDetails.style.display = 'block';
            } else {
                creditCardDetails.style.display = 'none';
            }
        });
        
        // Format card number with spaces
        document.getElementById('cardNumber').addEventListener('input', function(e) {
            var target = e.target;
            var position = target.selectionEnd;
            var length = target.value.length;
            
            target.value = target.value.replace(/[^\d]/g, '').replace(/(.{4})/g, '$1 ').trim();
            
            target.selectionEnd = position += ((target.value.charAt(position - 1) === ' ' && target.value.charAt(length - 1) === ' ' && length !== target.value.length) ? 1 : 0);
        });
        
        // Format expiry date with slash
        document.getElementById('expiryDate').addEventListener('input', function(e) {
            var target = e.target;
            var position = target.selectionEnd;
            var length = target.value.length;
            
            target.value = target.value.replace(/[^\d]/g, '').replace(/^(.{2})/, '$1/').substr(0, 5);
            
            target.selectionEnd = position += ((target.value.charAt(position - 1) === '/' && target.value.charAt(length - 1) === '/' && length !== target.value.length) ? 1 : 0);
        });
    </script>
</body>
</html>
