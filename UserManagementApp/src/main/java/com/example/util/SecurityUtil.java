package com.example.util;

import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern USERNAME_PATTERN
            = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
    private static final Pattern NAME_PATTERN
            = Pattern.compile("^[a-zA-Z\\s-]{2,50}$");

    private static final int BCRYPT_ROUNDS = 12;

    // Proper password hashing with BCrypt
    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return BCrypt.hashpw(password.trim(), BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    // Proper password verification with BCrypt
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword.trim(), hashedPassword);
        } catch (Exception e) {
            System.err.println("Password check error: " + e.getMessage());
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static String escapeHtml(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Helper method to verify BCrypt is working
    public static void testBcrypt() {
        String password = "test123";
        String hash = hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hash);
        System.out.println("Verification: " + checkPassword(password, hash));
        System.out.println("Wrong password test: " + checkPassword("wrong", hash));
    }

    // Main method for testing
    public static void main(String[] args) {
        testBcrypt();
    }
}
