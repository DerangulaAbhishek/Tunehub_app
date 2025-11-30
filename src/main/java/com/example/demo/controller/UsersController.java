package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.services.UsersServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UsersController {

    @Autowired
    private UsersServiceImplementation service;

    @PostMapping("/register")
    public String register(@RequestBody Users user) {
        if (service.emailExist(user.getEmail())) {
            return "Email already exists";
        }
        service.addUser(user);
        return "Registered successfully!";
    }

    @PostMapping("/validate")
    public Map<String, String> validate(@RequestBody Users user) {
        Map<String, String> response = new HashMap<>();

        boolean valid = service.validateUser(user.getEmail(), user.getPassword());
        if (!valid) {
            response.put("status", "invalid");
            return response;
        }

        Users loggedInUser = service.getUser(user.getEmail());
        if (loggedInUser == null) {
            response.put("status", "invalid");
            return response;
        }

        // Determine role including premium
        String role = loggedInUser.getRole().trim().toLowerCase();
        if ("customer".equals(role) && loggedInUser.isPremium()) {
            role = "customer-premium";
        }

        // Success
        response.put("status", "success");
        response.put("role", role);

        // ⭐ Send full user information
        response.put("id", String.valueOf(loggedInUser.getId()));
        response.put("username", loggedInUser.getUsername());
        response.put("email", loggedInUser.getEmail());
        response.put("gender", loggedInUser.getGender());
        response.put("address", loggedInUser.getAddress());
        response.put("isPremium", String.valueOf(loggedInUser.isPremium()));

        // Redirect based on role
        switch (role) {
            case "admin":
                response.put("redirect", "/admin");
                break;
            case "customer":
            case "customer-premium":
                response.put("redirect", "/customerHome");
                break;
            default:
                response.put("redirect", "/login");
        }

        return response;
    }
}
