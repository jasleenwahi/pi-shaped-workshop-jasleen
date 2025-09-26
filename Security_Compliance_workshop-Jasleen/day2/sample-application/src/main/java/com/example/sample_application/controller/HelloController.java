package com.example.sample_application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class HelloController {

    // Vulnerable: password is hardcoded
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    @GetMapping("/user")
    public String getUser(@RequestParam String username) throws Exception {
        // Vulnerable: direct concatenation of user input into SQL query
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM users WHERE username = '" + username + "'"
        );

        if (rs.next()) {
            return "User found: " + rs.getString("username");
        } else {
            return "No user found.";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
            return "✅ Login successful";
        }
        return "❌ Invalid credentials";
    }
}