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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        
        userDAO = (UserDAO) getServletContext().getAttribute("userDAO");

        if (userDAO == null) {
            throw new ServletException("UserDAO not initialized in ServletContext. Did you set it in AppContextListener?");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        System.out.println("=== REGISTRATION ATTEMPT ===");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);

        //  Server-side validation
        if (!SecurityUtil.isValidUsername(username)) {
            request.setAttribute("error", "Invalid username. Use 3-20 alphanumeric characters.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!SecurityUtil.isValidPassword(password)) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!SecurityUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!SecurityUtil.isValidName(firstName)) {
            request.setAttribute("error", "Invalid first name. Use 2-50 letters, spaces, or hyphens.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        if (!SecurityUtil.isValidName(lastName)) {
            request.setAttribute("error", "Invalid last name. Use 2-50 letters, spaces, or hyphens.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        //  Check if username already exists
        if (userDAO.findByUsername(username) != null) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        //  Check if email already exists
        if (userDAO.findByEmail(email) != null) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        //  Hash password before saving
        String hashedPassword = SecurityUtil.hashPassword(password);

        //  Create new user with default "user" role
        User user = new User();

        user.setUsername(username);
        user.setPassword(hashedPassword); // now hashed
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("user"); // Default role

        boolean success = userDAO.createUser(user);

        if (success) {
            // Flash message stored in session so it survives redirect
            HttpSession session = request.getSession();
            session.setAttribute("success", "Registration successful. Please login.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
