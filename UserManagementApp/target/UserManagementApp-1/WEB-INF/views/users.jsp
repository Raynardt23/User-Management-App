<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Admin - User Management</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    </head>
    <body>
        <div class="app-container">
            <header class="app-header">
                <div class="container">
                    <div class="navbar">
                        <div class="app-title">User Management System - Admin Panel</div>
                        <div class="nav-links">
                            <a href="${pageContext.request.contextPath}/profile">My Profile</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container main-content">
                <div class="card">
                    <h2 class="card-title">User Management</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Role</th>
                                    <th>Created At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${allUsers}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.username}</td>
                                        <td>${user.email}</td>
                                        <td>${user.firstName}</td>
                                        <td>${user.lastName}</td>
                                        <td>
                                            <span class="role-badge role-${user.role}">${user.role}</span>
                                        </td>
                                        <td><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/edit?id=${user.id}" class="btn btn-primary btn-sm">Edit</a>
                                            <a href="${pageContext.request.contextPath}/admin/delete?id=${user.id}" class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                                        </td>
                                    </tr>    
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
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