<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Profile - User Management System</title>
       <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    </head>
    <body>
        <div class="app-container">
            <header class="app-header">
                <div class="container">
                    <div class="navbar">
                        <div class="app-title">User Management System</div>
                        <div class="nav-links">
                            <span class="user-welcome">Welcome, ${user.firstName} (${user.role})</span>
                            <c:if test="${user.role eq 'admin'}">
                                <a href="admin" class="btn btn-outline">Admin Panel</a>
                            </c:if>
                            <a href="logout" class="btn btn-outline">Logout</a>
                        </div>
                    </div>
                </div>
            </header>

            <main class="container main-content">
                <div class="card">
                    <h2 class="card-title">User Profile</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <div class="profile-header">
                        <p class="welcome-text">Welcome, ${user.firstName} ${user.lastName} (${user.role})</p>
                        <p class="member-since">Member since: <fmt:formatDate value="${user.createdAt}" pattern="MMM dd, yyyy"/></p>
                    </div>

                    <div class="section-title">Update Profile</div>

                    <form action="profile" method="post" class="profile-form">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="${user.email}" class="form-control" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="firstName">First Name:</label>
                                <input type="text" id="firstName" name="firstName" value="${user.firstName}" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="lastName">Last Name:</label>
                                <input type="text" id="lastName" name="lastName" value="${user.lastName}" class="form-control" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </form>
                </div>

                <c:if test="${user.role eq 'admin'}">
                    <div class="card card-wide">
                        <h3 class="section-title">All Users (Admin View)</h3>
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
                                    <c:forEach items="${allUsers}" var="u">
                                        <tr>
                                            <td>${u.id}</td>
                                            <td>${u.username}</td>
                                            <td>${u.email}</td>
                                            <td>${u.firstName}</td>
                                            <td>${u.lastName}</td>


                                            <td>
                                                <span class="role-badge role-${u.role}">${u.role}</span>
                                                <c:if test="${user.role eq 'admin' and user.id ne u.id}">
                                                    <form action="profile" method="post" class="role-form" style="display: inline-block; margin-left: 8px;">
                                                        <input type="hidden" name="action" value="changeRole">
                                                        <input type="hidden" name="userId" value="${u.id}">
                                                        <select name="role" onchange="this.form.submit()" style="margin: 0; padding: 4px;">
                                                            <option value="user" ${u.role eq 'user' ? 'selected' : ''}>User</option>
                                                            <option value="admin" ${u.role eq 'admin' ? 'selected' : ''}>Admin</option>
                                                        </select>
                                                    </form>
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate value="${u.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                            <td>
                                                <c:if test="${user.id ne u.id}">
                                                    <a href="admin/delete?id=${u.id}" class="btn btn-danger btn-sm" 
                                                       onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                                                </c:if>
                                            </td>
                                        </tr>    
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </main>

            <footer class="app-footer">
                <div class="container">
                    <p>&copy; 2025 User Management System</p>
                </div>
            </footer>
        </div>
    </body>
</html>
