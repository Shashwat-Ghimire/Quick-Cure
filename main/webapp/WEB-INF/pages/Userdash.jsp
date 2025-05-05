<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <!-- Sidebar -->
    <input type="checkbox" id="sidebar-toggle" class="sidebar-checkbox">
    <aside class="sidebar">
        <div class="logo">Quick Cure</div>
        <div class="toggle-section">
            <span class="toggle-label">Menu</span>
            <label for="sidebar-toggle" class="toggle-btn">☰</label>
        </div>
        <ul>
            <li><a href="${pageContext.request.contextPath}/Userdash.do">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/product.do">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/settings.do">Profile</a></li>
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
                    <p class="user-info">Username: User</p>
                    <p class="user-info">Email: admin@quickcure.com</p>
                    <a href="${pageContext.request.contextPath}/settings.do" class="dropdown-item">Settings</a>
                    <a href="${pageContext.request.contextPath}/login.do" class="dropdown-item">Sign Out</a>
                </div>
            </div>
        </header>

        <!-- Dashboard Content -->
        <section class="dashboard-content">
            <h1>Welcome, Likhil!</h1>
            <p>Your healthcare dashboard is up to date</p>

            <!-- Stats -->
            <div class="user-stats">
                <div class="stat-card">
                    <h3>Reminders</h3>
                    <p>2</p>
                    <span class="change positive">+1 vs last month</span>
                </div>
                <div class="stat-card">
                    <h3>Recent Purchases</h3>
                    <p>8</p>
                    <span class="change positive">+5% vs last month</span>
                </div>
                <div class="stat-card">
                    <h3>Health Records</h3>
                    <p>12</p>
                </div>
                <div class="stat-card">
                    <h3>Referrals</h3>
                    <p>2</p>
                    <span class="change negative">-8% vs last month</span>
                </div>
            </div>

            <!-- Main Sections -->
            <div class="main-sections">
                <!-- Upcoming Appointments -->
                <section class="appointments">
                    <div class="section-header">
                        <h2>Health Reminders</h2>
                        <a href="#" class="view-all">View All</a>
                    </div>
                    <div class="appointment-card">
                        <div class="doctor-info">
                            <span class="doctor-initials">PC</span>
                            <div>
                                <h3>Paracetamol</h3>
                                <p>500mg</p>
                            </div>
                        </div>
                        <p class="appointment-time">Apr 19-30, 2025 • 10:00 AM</p>
                        <div class="appointment-actions">
                            <button class="reschedule-btn">Restock</button>
                            <button class="join-btn">Take Meds</button>
                        </div>
                    </div>
                    <div class="appointment-card">
                        <div class="doctor-info">
                            <span class="doctor-initials">Vit.D</span>
                            <div>
                                <h3>Vitamin</h3>
                                <p>D</p>
                            </div>
                        </div>
                        <p class="appointment-time">Apr 22-27, 2025 • 3:00 PM</p>
                        <div class="appointment-actions">
                            <button class="reschedule-btn">Restock</button>
                            <button class="join-btn">Take Meds</button>
                        </div>
                    </div>
                </section>

                <!-- Recent Purchases -->
                <section class="purchases">
                    <div class="section-header">
                        <h2>Recent Purchases</h2>
                        <a href="#" class="view-all">View All</a>
                    </div>
                    <div class="purchase-card">
                        <span class="purchase-icon">📦</span>
                        <div class="purchase-details">
                            <h3>Monthly Prescription Bundle</h3>
                            <p>Order #QC857402 • Apr 14, 2025</p>
                        </div>
                        <div class="purchase-status">
                            <p>$87.50</p>
                            <span class="status delivered">Delivered</span>
                        </div>
                    </div>
                    <div class="purchase-card">
                        <span class="purchase-icon">📦</span>
                        <div class="purchase-details">
                            <h3>Allergy Medication</h3>
                            <p>Order #QC857396 • Apr 10, 2025</p>
                        </div>
                        <div class="purchase-status">
                            <p>$24.99</p>
                            <span class="status shipped">Shipped</span>
                        </div>
                    </div>
                    <div class="purchase-card">
                        <span class="purchase-icon">📦</span>
                        <div class="purchase-details">
                            <h3>Vitamin Supplements</h3>
                            <p>Order #QC857380 • Mar 29, 2025</p>
                        </div>
                        <div class="purchase-status">
                            <p>$35.75</p>
                            <span class="status delivered">Delivered</span>
                        </div>
                    </div>
                </section>
            </div>
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
        <a href="#" class="nav-item">Appointments</a>
        <a href="#" class="nav-item">Profile</a>
        <a href="#" class="nav-item">Settings</a>
    </nav>
</body>
</html>