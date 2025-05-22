<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="Author" content="Shashwat Ghimire">
    <meta name="description" content="About Quick Cure and our team">
    <title>Quick Cure - About Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="header.jsp" %>

    <!-- About Section -->
    <section>
        <div class="aboutbox">
            <div class="aboutimgbox">
                <img src="${pageContext.request.contextPath}/images/medicine.jpg" alt="Pharmacy Background" id="aboutimg">
            </div>
            <div class="infoabout">
                <h5 id="abouth5">ABOUT</h5>
                <h2 id="abouth2">Your trusted online pharmacy for quality medicines and healthcare products</h2>
                <p id="aboutp">Quick Cure offers a seamless shopping experience for all your medical needs. Click below to explore our products.</p>
                <a href="products.jsp"><button id="productbut">SHOP NOW!</button></a>
            </div>
        </div>
    </section>

    <!-- Story Section -->
    <section>
        <div class="storybox">
            <div class="story">
                <h5>WHO WE ARE</h5>
                <h2>The story of Quick Cure</h2>
                <p>
                    Quick Cure was founded to simplify access to healthcare products, eliminating the hassle of long queues and limited stock. 
                    Our platform connects customers with trusted suppliers, ensuring high-quality medicines and fast delivery. 
                    We prioritize reliability, affordability, and customer satisfaction, making healthcare accessible from the comfort of your home.
                </p>
            </div>
            <div class="storyimg">
                <img src="https://source.unsplash.com/random/500x300/?medicine" alt="Pharmacy Queue">
            </div>
        </div>
    </section>

    <!-- Mission Section -->
    <section>
        <div class="mission">
            <h4 id="missionh4">OUR MISSION</h4>
            <p id="missionp">
                At Quick Cure, we aim to provide a convenient and reliable way to access high-quality healthcare products. 
                Our goal is to simplify your shopping experience, offer a wide range of medicines, and ensure timely delivery, 
                so you can focus on your health without worry.
            </p>
            <div class="missionimg">
                <img src="https://source.unsplash.com/random/600x400/?healthcare" alt="Online Pharmacy" id="misimg">
            </div>
        </div>
    </section>

    <!-- Team Introduction -->
    <section>
        <div class="infoteambox">
            <h5 id="infoteam">MEET OUR TEAM</h5>
            <h6 id="capteam">The dedicated professionals behind Quick Cure</h6>
            <p id="caption">Click a team member's picture to view their portfolio.</p>
        </div>
    </section>

    <!-- Team Portfolio -->
    <section>
        <div class="portfoliobox">
            <div class="portfolio">
                <div class="image" id="team1">
                    <a href="#"><img src="https://source.unsplash.com/random/175x175/?person1" alt="Shashwat Ghimire"></a>
                </div>
                <div class="aboutmem">
                    <h3 class="membername">Shashwat Ghimire</h3>
                    <h5 class="role">Team Leader / Frontend</h5>
                </div>
            </div>
            <div class="portfolio">
                <div class="image" id="team2">
                    <a href="#"><img src="https://source.unsplash.com/random/175x175/?person2" alt="Likhil Dahal"></a>
                </div>
                <div class="aboutmem">
                    <h3 class="membername">Likhil Dahal</h3>
                    <h5 class="role">Backend</h5>
                </div>
            </div>
            <div class="portfolio">
                <div class="image" id="team3">
                    <a href="#"><img src="https://source.unsplash.com/random/175x175/?person3" alt="Ishika KC"></a>
                </div>
                <div class="aboutmem">
                    <h3 class="membername">Ishika KC</h3>
                    <h5 class="role">Report</h5>
                </div>
            </div>
            <div class="portfolio">
                <div class="image" id="team4">
                    <a href="#"><img src="https://source.unsplash.com/random/175x175/?person4" alt="Pemba Tshering Tamang"></a>
                </div>
                <div class="aboutmem">
                    <h3 class="membername">Pemba Tshering Tamang</h3>
                    <h5 class="role">Report</h5>
                </div>
            </div>
            <div class="portfolio">
                <div class="image" id="team5">
                    <a href="#"><img src="https://source.unsplash.com/random/175x175/?person5" alt="Niroj Basnet"></a>
                </div>
                <div class="aboutmem">
                    <h3 class="membername">Niroj Basnet</h3>
                    <h5 class="role">Database</h5>
                </div>
            </div>
        </div>
    </section>

    <!-- Message Section -->
    <section>
        <div class="message">
            <p id="messagep">
                We value your feedback to enhance our services and provide a better experience. 
                Share your thoughts to help us improve Quick Cure.
            </p>
        </div>
    </section>


    <!-- Include Footer -->
    <%@ include file="footer.jsp" %>
</body>
</html>