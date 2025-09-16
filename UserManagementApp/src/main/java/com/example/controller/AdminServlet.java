package com.example.controller;

import com.example.dao.UserDAO;
import com.example.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        
        // Get the shared UserDAO instance from the application context
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

        // Check if user is logged in and is admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            request.setAttribute("error", "Access denied. Admin privileges required.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        String action = request.getPathInfo();

        if (action != null) {
            switch (action) {
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } else {
            listUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            request.setAttribute("error", "Access denied. Admin privileges required.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        String action = request.getPathInfo();

        if (action != null) {
            switch (action) {
                case "/update":
                    updateUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } else {
            listUsers(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("allUsers", userDAO.getAllUsers());
        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userId != null) {
            try {
                User user = userDAO.findById(Integer.parseInt(userId));
                if (user != null) {
                    request.setAttribute("editUser", user);
                    request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                
            }
        }
        request.setAttribute("error", "User not found.");
        listUsers(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userId = request.getParameter("id");
        
        listUsers(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userId != null) {
            try {
                User currentUser = (User) request.getSession().getAttribute("user");
                int id = Integer.parseInt(userId);

                // Prevent self-deletion
                if (currentUser.getId() == id) {
                    request.setAttribute("error", "You cannot delete your own account.");
                } else if (userDAO.deleteUser(id)) {
                    request.setAttribute("success", "User deleted successfully.");
                } else {
                    request.setAttribute("error", "Failed to delete user.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid user ID.");
            }
        }
        listUsers(request, response);
    }
}
