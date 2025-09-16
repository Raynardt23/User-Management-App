<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Register - User Management System</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    </head>
    <body>
        <div class="app-container">
            <header class="app-header">
                <div class="container">
                    <div class="navbar">
                        <div class="app-title">User Management System</div>
                        <div class="nav-links">
                            <a href="login">Login</a>
                            <a href="register">Register</a>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container main-content">
                <div class="card">
                    <h2 class="card-title">Register</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <form action="register" method="post" class="auth-form">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="firstName">First Name:</label>
                                <input type="text" id="firstName" name="firstName" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="lastName">Last Name:</label>
                                <input type="text" id="lastName" name="lastName" class="form-control" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary btn-block">Register</button>
                    </form>

                    <p class="auth-link">
                        Already have an account? <a href="login" class="text-link">Login here</a>
                    </p>
                </div>
            </main>

            <footer class="app-footer">
                <div class="container">
                    <p>&copy; 2025 User Management System</p>
                </div>
            </footer>
        </div>
    </body>
</html>
