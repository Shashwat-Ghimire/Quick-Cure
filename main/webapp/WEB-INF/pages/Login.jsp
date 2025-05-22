<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Meta tags for character encoding and responsive viewport -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Page title -->
    <title>Login</title>
    <!-- Link to external CSS file using context path for proper resolution -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="logo">
		<div class=logoimg></div>
	</div>
    <!-- Main container for the login form, centered on the page -->
    <div class="login-container">
        <!-- Form container -->
        <div class="form-container">
            <!-- Form heading -->
            <h2>Welcome back</h2>
            <!-- Subtitle text -->
            <p class="subtitle">Enter your credentials to access your account</p>
          
            <!-- Login form, submits to Homecontroller servlet -->
            <form id="loginForm" action="Homecontroller" method="post">
                <c:if test="${not empty loginError}">
                    <div class="error-message">
                        <span class="error">${loginError}</span>
                    </div>
                </c:if>
                <!-- Email field with error handling and pre-fill -->
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${param.email}">
                </div>
                <!-- Password field with error handling -->
                <div class="form-group password-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password">
                </div>                              
                <!-- Submit button -->
                <button type="submit" class="submit-btn">Sign in</button>
                <!-- Sign-up link for new users -->
                <p class="sign-up-link">Don't have an account? <a href="${pageContext.request.contextPath}/register.do">Sign up</a></p>
            </form>
        </div>
    </div>
</body>
</html>