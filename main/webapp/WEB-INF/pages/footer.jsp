<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Footer</title>
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

        footer {
            background-color: #0056b3;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
            padding: 40px 20px 20px;
            bottom: 0;
        }

        .footer-content {
            max-width: auto;
            margin: 0 auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            gap: 20px;
        }

        .footer-section {
            flex: 1;
            min-width: 200px;
            margin-bottom: 20px;
        }

        .footer-section h3 {
            font-size: 18px;
            font-weight: bold;
            color: #fff;
            margin-bottom: 15px;
        }

        .footer-section p,
        .footer-section li {
            font-size: 14px;
            color: #fff;
            line-height: 1.6;
            margin-bottom: 8px;
        }

        /* Contact Info with Icon */
        .contact-item {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-bottom: 20px;
        }

        .contact-item i {
            font-size: 16px;
            color: #fff;
        }

        .contact-item span {
            font-size: 14px;
            color: #fff;
        }

        /* Social Links with Icons */
        .social-links {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .social-link {
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
            font-size: 14px;
            color: #fff;
            transition: color 0.3s ease;
        }

        .social-link i {
            font-size: 16px;
            color: #fff;
            transition: color 0.3s ease;
        }

        .social-link:hover,
        .social-link:hover i {
            color: #57c5f4;
        }

        /* Quick Links */
        .footer-section ul {
            list-style: none;
        }

        .footer-link {
            text-decoration: none;
            color: #fff;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .footer-link:hover {
            text-decoration: underline;
            color: #57c5f4;
        }

        /* Footer Bottom */
        .footer-bottom {
            text-align: center;
            padding: 15px 0;
            border-top: 1px solid #eee;
            margin-top: 20px;
        }

        .footer-bottom p {
            font-size: 12px;
            color: #fff;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .footer-content {
                flex-direction: column;
                align-items: center;
                text-align: center;
            }

            .social-links {
                align-items: center;
            }

            .footer-section ul {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
        }
    </style>
</head>
<body>
    <!-- Footer Section -->
    <footer>
        <div class="container footer-content">
            <!-- Contact Info -->
            <div class="footer-section">
                <h3>Contact Info</h3>
                <div class="contact-item">
                    <i class="fa fa-envelope"></i>
                    <span>Email: support@quickcure.com</span>
                </div>
                <div class="contact-item">
                    <i class="fa fa-phone"></i>
                    <span>Phone: +977-9898989898</span>
                </div>
                <div class="contact-item">
                    <i class="fa fa-clock-o"></i>
                    <span>Hours: 24/7</span>
                </div>
            </div>
            <!-- Location -->
            <div class="footer-section">
                <h3>Location</h3>
                <p>Naxal, Kathmandu, Nepal</p>
                <p>KTM 19230, NP</p>
            </div>
            <!-- Social Media -->
            <div class="footer-section">
                <h3>Follow Us</h3>
                <div class="social-links">
                    <a href="https://facebook.com/quickcure" class="social-link">
                        <i class="fa fa-facebook"></i>
                        <span>Facebook</span>
                    </a>
                    <a href="https://twitter.com/quickcure" class="social-link">
                        <i class="fa fa-twitter"></i>
                        <span>Twitter</span>
                    </a>
                    <a href="https://instagram.com/quickcure" class="social-link">
                        <i class="fa fa-instagram"></i>
                        <span>Instagram</span>
                    </a>
                </div>
            </div>
            <!-- Quick Links -->
            <div class="footer-section">
                <h3>Quick Links</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/home.do" class="footer-link">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/about.do" class="footer-link">About</a></li>
                    <li><a href="${pageContext.request.contextPath}/product.do" class="footer-link">Products</a></li>
                    <li><a href="${pageContext.request.contextPath}/profile.do" class="footer-link">Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/mycart.do" class="footer-link">Cart</a></li>
                </ul>
            </div>
        </div>
        <div class="footer-bottom">
            <p>© 2025 Quick Cure. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>