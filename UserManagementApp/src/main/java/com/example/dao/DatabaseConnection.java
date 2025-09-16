package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DB_NAME;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }

            Properties prop = new Properties();
            prop.load(input);

            URL = prop.getProperty("db.url");       // e.g., jdbc:mysql://localhost:3306/
            DB_NAME = prop.getProperty("db.name");  // e.g., userdb
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");

            // Explicitly load MySQL driver once
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("MySQL JDBC driver loaded successfully.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MySQL JDBC driver not found", e);
            }

            System.out.println("Database config loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DatabaseConnection", e);
        }
    }

    // Connect to MySQL server without specifying DB
    public static Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(URL + "?useSSL=false&serverTimezone=UTC", USER, PASSWORD);
    }

    // Connect to the actual database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME + "?useSSL=false&serverTimezone=UTC", USER, PASSWORD);
    }

    // Create database if not exists
    public static void createDatabaseIfNotExists() {
        try (Connection conn = getServerConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("Database checked/created: " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create database", e);
        }
    }
}
