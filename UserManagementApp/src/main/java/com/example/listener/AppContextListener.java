package com.example.listener;

import com.example.dao.DatabaseConnection;
import com.example.dao.UserDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
public void contextInitialized(ServletContextEvent sce) {
    try {
        System.out.println("Initializing AppContext...");
        DatabaseConnection.createDatabaseIfNotExists();
        UserDAO userDAO = new UserDAO();
        userDAO.createUsersTableIfNotExists();
        userDAO.createDefaultAdmin();
        sce.getServletContext().setAttribute("userDAO", userDAO);
        System.out.println("AppContext initialized successfully.");
    } catch (Exception e) {
        e.printStackTrace(); // ensure you see it
        throw new RuntimeException("Failed to initialize AppContext", e);
    }
}

@Override
public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("ServletContext destroyed.");

    // Shut down MySQL cleanup thread
    //com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();

    // Deregister JDBC drivers to avoid memory leaks
    java.util.Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
        java.sql.Driver driver = drivers.nextElement();
        try {
            java.sql.DriverManager.deregisterDriver(driver);
            System.out.println("Deregistered JDBC driver: " + driver);
        } catch (SQLException e) {
            System.err.println("Error deregistering driver: " + driver);
            e.printStackTrace();
        }
    }
}

}
