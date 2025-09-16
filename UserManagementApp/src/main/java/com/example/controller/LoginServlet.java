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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
    if (session != null && session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/profile");
        return;
    }

    
    request.getRequestDispatcher("/login.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + password + "'");

        try {
            User user = userDAO.findByUsername(username);
            System.out.println("User found: " + (user != null));

            if (user != null) {
                System.out.println("Stored password hash: " + user.getPassword());
                System.out.println("Input password hash attempt: " + SecurityUtil.hashPassword(password));

                boolean passwordMatch = SecurityUtil.checkPassword(password, user.getPassword());
                System.out.println("Password check result: " + passwordMatch);
            }

            if (user != null && SecurityUtil.checkPassword(password, user.getPassword())) {
                System.out.println("Login SUCCESS!");
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("profile");
            } else {
                System.out.println("Login FAILED!");
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.err.println("=== LOGIN ERROR DETAILS ===");
            e.printStackTrace();
            System.err.println("Error message: " + e.getMessage());

            request.setAttribute("error", "Login error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
