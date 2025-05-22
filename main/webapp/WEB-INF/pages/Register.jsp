<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Account</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <div class="container">
        <div class="left-section">
            <div class="logo"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt="Quick Cure" class="logoimg"><span class="logotext">Quick Cure</span></div>
            
            <h1>Healthcare at your fingertips</h1>
            <p><b>Your trusted online pharmacy for fast, reliable medicine delivery.</b></p>
        </div>

        <div class="right-section">
            <div class="form-container">
                <h2>Create an account</h2>
                <p class="subtitle">Enter your information to get started with QuickCure</p>
                <form id="registrationForm" action="${pageContext.request.contextPath}/register.do" method="post">
                    <!-- First Name and Last Name side by side -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name:</label>
                            <input type="text" id="firstName" name="firstName" value="${param.firstName}">
                            <c:if test="${not empty errors.firstName}">
                                <span class="error">${errors.firstName}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name:</label>
                            <input type="text" id="lastName" name="lastName" value="${param.lastName}">
                            <c:if test="${not empty errors.lastName}">
                                <span class="error">${errors.lastName}</span>
                            </c:if>
                        </div>
                    </div>

                    <!-- Username and Email side by side -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="${param.username}">
                            <c:if test="${not empty errors.username}">
                                <span class="error">${errors.username}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="${param.email}">
                            <c:if test="${not empty errors.email}">
                                <span class="error">${errors.email}</span>
                            </c:if>
                        </div>
                    </div>

                    <!-- Phone Number and Gender side by side -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="phone">Phone Number:</label>
                            <input type="text" id="phone" name="phone" value="${param.phone}" placeholder="+12345678901234">
                            <c:if test="${not empty errors.phone}">
                                <span class="error">${errors.phone}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender:</label>
                            <select id="gender" name="gender">
                                <option value="" disabled selected>Choose your gender</option>
                                <option value="Male" ${param.gender == 'Male'}>Male</option>
                                <option value="Female" ${param.gender == 'Female'}>Female</option>
                            </select>
                            <c:if test="${not empty errors.gender}">
                                <span class="error">${errors.gender}</span>
                            </c:if>
                        </div>
                    </div>

                    <!-- Address (full width to match button) -->
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" value="${param.address}" class="full-width">
                        <c:if test="${not empty errors.address}">
                            <span class="error">${errors.address}</span>
                        </c:if>
                    </div>

                    <!-- Password and Confirm Password side by side -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" value="${param.password}">
                            <c:if test="${not empty errors.password}">
                                <span class="error">${errors.password}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="retypePassword">Confirm Password:</label>
                            <input type="password" id="retypePassword" name="retypePassword" value="${param.retypePassword}">
                            <c:if test="${not empty errors.retypePassword}">
                                <span class="error">${errors.retypePassword}</span>
                            </c:if>
                        </div>
                    </div>

                    <!-- Terms and Conditions checkbox -->
                    <div class="form-group checkbox-group">
                        <input type="checkbox" id="terms" name="terms" ${not empty param.terms ? 'checked' : ''}>
                        <label for="terms">I agree to the Terms of Service and Privacy Policy</label>
                        <c:if test="${not empty errors.terms}">
                            <span class="error">${errors.terms}</span>
                        </c:if>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="submit-btn">Create Account</button>

                    <!-- Sign-in link -->
                    <p class="sign-in-link">Already have an account? <a href="${pageContext.request.contextPath}/login.do">Sign in</a></p>
                </form>
            </div>
        </div>
    </div>
</body>
</html>