package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        boolean isValid = service.validateUser(email, password);
//        if (!isValid) {
//            System.out.println("Invalid credentials for email: " + email);
//            return "home"; // Redirect to home page if login is invalid
//        }
        if(service.validateUser(email, password)==true) {
        	String role= service.getRole(email);
        	if(role.equals("admin")) {
        		return "adminHome";
        	}
        	else {
        		return "customerHome";
        	}
        }
        //System.out.println();
        System.out.println("User validated successfully for email: " + email);
        return "login"; // Redirect to login page after successful validation
    }
}
