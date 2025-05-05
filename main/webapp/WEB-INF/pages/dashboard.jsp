<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css">
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
            <li><a href="${pageContext.request.contextPath}/dashboard.do">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/orders.do">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/product.do">Products</a></li>
            <li><a href="${pageContext.request.contextPath}/cusotmer.do">Customers</a></li>
            <li><a href="${pageContext.request.contextPath}/settings.do">Settings</a></li>
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
                    <p class="user-info">Username: Admin</p>
                    <p class="user-info">Email: admin@quickcure.com</p>
                    <a href="${pageContext.request.contextPath}/settings.do" class="dropdown-item">Settings</a>
                    <a href="${pageContext.request.contextPath}/login.do" class="dropdown-item">Sign Out</a>
                </div>
            </div>
        </header>

        <!-- Dashboard Content -->
        <section class="dashboard-content">
            <h1>Dashboard</h1>

            <!-- Stats -->
            <div class="admin-stats">
    <div class="stat-card">
        <h3>Total Orders</h3>
        <p>${dashboard.totalOrders}</p>
        <span class="change positive">+12.5% vs last month</span>
    </div>
    <div class="stat-card">
        <h3>Total Revenue</h3>
        <p>$${dashboard.totalRevenue}</p>
        <span class="change positive">+8.2% vs last month</span>
    </div>
    <div class="stat-card">
        <h3>Active Customers</h3>
        <p>${dashboard.activeCustomers}</p>
        <span class="change positive">+3.1% vs last month</span>
    </div>
    <div class="stat-card">
        <h3>Low Stock Items</h3>
        <p>${dashboard.lowStockItems}</p>
        <span class="change negative">-2.5% vs last month</span>
    </div>
</div>

            <!-- Main Sections -->
            <div class="main-sections">
                <!-- Sales Overview -->
                <section class="sales-overview">
                    <h2>Sales Overview</h2>
                    <p>Monthly sales performance</p>
                    <div class="graph-placeholder">
                        <!-- Placeholder for graph -->
                        <canvas id="salesGraph"></canvas>
                    </div>
                </section>

                <!-- Top Products -->
                <section class="top-products">
                    <h2>Top Products</h2>
                    <p>Best performing products this month</p>
                    <div class="product-item">
                        <span class="product-icon">💊</span>
                        <div class="product-details">
                            <h3>Paracetamol 500mg</h3>
                            <p>Pain Relief</p>
                        </div>
                        <div class="product-stats">
                            <p>1,234 sold</p>
                            <span class="change positive">+12.5%</span>
                        </div>
                    </div>
                    <div class="product-item">
                        <span class="product-icon">💊</span>
                        <div class="product-details">
                            <h3>Vitamin C Complex</h3>
                            <p>Vitamins and Supplements</p>
                        </div>
                        <div class="product-stats">
                            <p>982 sold</p>
                            <span class="change negative">-8.3%</span>
                        </div>
                    </div>
                    <div class="product-item">
                        <span class="product-icon">💊</span>
                        <div class="product-details">
                            <h3>Allergy Relief Tabs</h3>
                            <p>Allergy</p>
                        </div>
                        <div class="product-stats">
                            <p>879 sold</p>
                            <span class="change negative">-2.1%</span>
                        </div>
                    </div>
                    <div class="product-item">
                        <span class="product-icon">💊</span>
                        <div class="product-details">
                            <h3>Blood Pressure Monitor</h3>
                            <p>Medical Devices</p>
                        </div>
                        <div class="product-stats">
                            <p>654 sold</p>
                            <span class="change positive">+5.7%</span>
                        </div>
                    </div>
                    <div class="product-item">
                        <span class="product-icon">💊</span>
                        <div class="product-details">
                            <h3>Hand Sanitizer</h3>
                            <p>Personal Care</p>
                        </div>
                        <div class="product-stats">
                            <p>521 sold</p>
                            <span class="change negative">-1.4%</span>
                        </div>
                    </div>
                </section>
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
                        <tr>
                            <td>ORD-1234</td>
                            <td>Shashwat Ghimire</td>
                            <td>2023-04-12</td>
                            <td>3</td>
                            <td>$129.99</td>
                            <td><span class="status delivered">Delivered</span></td>
                            <td><a href="#" class="details-btn">🔍</a></td>
                        </tr>
                        <tr>
                            <td>ORD-1235</td>
                            <td>Likhil Dahal</td>
                            <td>2023-04-12</td>
                            <td>2</td>
                            <td>$79.95</td>
                            <td><span class="status shipped">Shipped</span></td>
                            <td><a href="#" class="details-btn">🔍</a></td>
                        </tr>
                        <tr>
                            <td>ORD-1236</td>
                            <td>Ishika KC</td>
                            <td>2023-04-11</td>
                            <td>4</td>
                            <td>$249.99</td>
                            <td><span class="status processing">Processing</span></td>
                            <td><a href="#" class="details-btn">🔍</a></td>
                        </tr>
                        <tr>
                            <td>ORD-1237</td>
                            <td>Niroj Basnet</td>
                            <td>2023-04-11</td>
                            <td>1</td>
                            <td>$34.50</td>
                            <td><span class="status cancelled">Cancelled</span></td>
                            <td><a href="#" class="details-btn">🔍</a></td>
                        </tr>
                        <tr>
                            <td>ORD-1238</td>
                            <td>Pemba Tshering Tamang</td>
                            <td>2023-04-10</td>
                            <td>5</td>
                            <td>$199.95</td>
                            <td><span class="status delivered">Delivered</span></td>
                            <td><a href="#" class="details-btn">🔍</a></td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </section>
    </main>
<!-- JavaScript to render the graph -->
<script>
    // Get the canvas element
    const ctx = document.getElementById('salesGraph').getContext('2d');

    // Create the line chart
    new Chart(ctx, {
        type: 'line', // Line graph type
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'], // X-axis labels (months)
            datasets: [{
                label: 'Sales ($)', // Legend label
                data: [2000, 3000, 4500, 6000, 5000, 4000], // Y-axis data (sales)
                borderColor: '#0059b3', // Line color
                backgroundColor: 'rgba(0, 89, 179, 0.2)', // Fill color under the line
                borderWidth: 2, // Line thickness
                fill: true, // Fill the area under the line
                tension: 0.3 // Smoothness of the line
            }]
        },
        options: {
            responsive: true, // Make the graph responsive
            maintainAspectRatio: false, // Allow custom height
            scales: {
                y: {
                    beginAtZero: true, // Start Y-axis at 0
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
                    enabled: true // Show tooltips on hover
                }
            }
        }
    });
</script>
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
        <a href="#" class="nav-item">Orders</a>
        <a href="#" class="nav-item">Products</a>
        <a href="#" class="nav-item">Customers</a>
        <a href="#" class="nav-item">Settings</a>
    </nav>
</body>
</html>