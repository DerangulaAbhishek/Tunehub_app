package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UsersService service;

    @PostMapping("/Register")
    public String addUsers(@ModelAttribute Users user) {
        boolean userStatus = service.emailExist(user.getEmail());
        if (!userStatus) {
            service.addUser(user);
            System.out.println("User added successfully");
        } else {
            System.out.println("User email exists already");
        }
        return "home"; // Redirect to home page after registration
    }

    @PostMapping("/validate")
    public String validateUser(@RequestParam("email") String email, 
                               @RequestParam("password") String password, HttpSession session, Model model) {
       
        
        if (service.validateUser(email, password)==true) {
            String role = service.getRole(email);
            session.setAttribute("email", email);
            if (role.equals("admin")) {
                return "adminHome";
            } else {
            	Users user=service.getUser(email);
            	boolean userStatus=user.isPremium();
            	model.addAttribute("isPremium",userStatus);
                return "customerHome";
            }
        }
        System.out.println("Invalid credentials for email: " + email);
        return "login"; // Redirect to login page after unsuccessful validation
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
