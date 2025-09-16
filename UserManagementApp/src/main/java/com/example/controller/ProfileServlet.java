package com.example.controller;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.SecurityUtil;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {

        userDAO = (UserDAO) getServletContext().getAttribute("userDAO");

        if (userDAO == null) {
            System.err.println("Failed to get UserDAO from application context");
        } else {
            System.out.println("Servlet initialized successfully with shared UserDAO");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // For admin users, load all users
        if ("admin".equals(user.getRole())) {
            request.setAttribute("allUsers", userDAO.getAllUsers());
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if ("changeRole".equals(action)) {

            handleRoleChange(request, response, sessionUser);
            return;

        }

        // Validate inputs
        if (!SecurityUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            forwardToProfile(request, response, sessionUser);
            return;
        }

        if (!SecurityUtil.isValidName(firstName)) {
            request.setAttribute("error", "Invalid first name.");
            forwardToProfile(request, response, sessionUser);
            return;
        }

        if (!SecurityUtil.isValidName(lastName)) {
            request.setAttribute("error", "Invalid last name.");
            forwardToProfile(request, response, sessionUser);
            return;
        }

        // Check if email is already taken by another user
        User existingUser = userDAO.findByEmail(email);
        if (existingUser != null && existingUser.getId() != sessionUser.getId()) {
            request.setAttribute("error", "Email already taken by another user.");
            forwardToProfile(request, response, sessionUser);
            return;
        }

        // Update user
        User userToUpdate = userDAO.findByUsername(sessionUser.getUsername());
        userToUpdate.setEmail(email);
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);

        // Only admins can change roles
        if ("admin".equals(sessionUser.getRole())) {
            String role = request.getParameter("role");
            if (role != null && ("admin".equals(role) || "user".equals(role))) {
                userToUpdate.setRole(role);
            }
        }

        if (userDAO.updateUser(userToUpdate)) {
            // Update session user
            sessionUser.setEmail(email);
            sessionUser.setFirstName(firstName);
            sessionUser.setLastName(lastName);
            session.setAttribute("user", sessionUser);

            request.setAttribute("success", "Profile updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update profile.");
        }

        forwardToProfile(request, response, sessionUser);
    }

    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        if ("admin".equals(user.getRole())) {
            request.setAttribute("allUsers", userDAO.getAllUsers());
        }
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    private void handleRoleChange(HttpServletRequest request, HttpServletResponse response, User sessionUser)
            throws ServletException, IOException {
        if (!"admin".equals(sessionUser.getRole())) {
            request.setAttribute("error", "Access denied. Admin privileges required.");
            forwardToProfile(request, response, sessionUser);
            return;
        }

        String userIdStr = request.getParameter("userId");
        String newRole = request.getParameter("role");

        if (userIdStr != null && newRole != null && ("admin".equals(newRole) || "user".equals(newRole))) {
            try {
                int userId = Integer.parseInt(userIdStr);
                User userToUpdate = userDAO.findById(userId);

                if (userToUpdate != null && userToUpdate.getId() != sessionUser.getId()) {
                    userToUpdate.setRole(newRole);
                    if (userDAO.updateUser(userToUpdate)) {
                        request.setAttribute("success", "User role updated successfully.");
                    } else {
                        request.setAttribute("error", "Failed to update user role.");
                    }
                } else {
                    request.setAttribute("error", "Cannot change your own role.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid user ID.");
            }
        } else {
            request.setAttribute("error", "Invalid role change request.");
        }

        forwardToProfile(request, response, sessionUser);
    }
}
