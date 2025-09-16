<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Edit User - Admin</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    </head>
    <body>
        <div class="app-container">
            <header class="app-header">
                <div class="container">
                    <div class="navbar">
                        <div class="app-title">User Management System - Admin Panel</div>
                        <div class="nav-links">
                            <a href="${pageContext.request.contextPath}/admin">Back to Users</a>
                            <a href="${pageContext.request.contextPath}/profile">My Profile</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container main-content">
                <div class="card">
                    <h2 class="card-title">Edit User</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/update" method="post" class="profile-form">
                        <input type="hidden" name="id" value="${editUser.id}">

                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="${editUser.username}" class="form-control" readonly>
                            <small>Username cannot be changed</small>
                        </div>

                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="${editUser.email}" class="form-control" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="firstName">First Name:</label>
                                <input type="text" id="firstName" name="firstName" value="${editUser.firstName}" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="lastName">Last Name:</label>
                                <input type="text" id="lastName" name="lastName" value="${editUser.lastName}" class="form-control" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="role">Role:</label>
                            <select id="role" name="role" class="form-control" required>
                                <option value="user" ${editUser.role eq 'user' ? 'selected' : ''}>User</option>
                                <option value="admin" ${editUser.role eq 'admin' ? 'selected' : ''}>Admin</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary">Update User</button>
                        <a href="${pageContext.request.contextPath}/admin" class="btn btn-outline">Cancel</a>
                    </form>
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
