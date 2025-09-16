<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Login - User Management System</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css" />
    </head>
    <body>
        <div class="app-container">
            <header class="app-header">
                <div class="container">
                    <div class="navbar">
                        <div class="app-title">User Management System</div>
                        <div class="nav-links">
                            <a href="${pageContext.request.contextPath}/login">Login</a>
                            <a href="${pageContext.request.contextPath}/register">Register</a>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container main-content">
                <div class="card">
                    <h2 class="card-title">Login</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                        <c:remove var="success" scope ="session"/>
                    </c:if>

                    <!-- Updated form action to include context path -->
                    <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                    </form>

                    <p class="auth-link">
                        Don't have an account? <a href="${pageContext.request.contextPath}/register" class="text-link">Register here</a>
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
