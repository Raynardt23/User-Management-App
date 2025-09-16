package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error-handler")
public class ErrorHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToErrorPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToErrorPage(request, response);
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to JSP inside WEB-INF/views
        request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    }
}

