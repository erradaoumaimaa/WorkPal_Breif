package org.example;

import db.JdbcConnection;
import model.Admin;
import model.Member;
import model.User;
import service.AuthService;
import java.sql.Connection;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<Connection> conn = JdbcConnection.getConnection();

        if (conn.isPresent()) {
            System.out.println("Connection is available.");
        } else {
            System.out.println("Failed to establish a connection.");
            return;
        }

        AuthService authService = new AuthService(conn.get());

        // d'inscription
        boolean registrationSuccess = authService.registerUser("John Doe", "john@example.com", "password123", "member");
        if (registrationSuccess) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed.");
        }

        // login
        Optional<User> loggedInUser = authService.loginUser("john@example.com", "password123");
        if (loggedInUser.isPresent()) {
            System.out.println("Login successful for: " + loggedInUser.get().getName());
        } else {
            System.out.println("Login failed.");
        }
    }
}
