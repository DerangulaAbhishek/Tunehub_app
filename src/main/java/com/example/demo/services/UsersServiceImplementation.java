package com.example.demo.services;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImplementation implements UsersService {
    @Autowired
    UsersReporsitory repo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String addUser(Users user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "User added successfully!";
    }

    @Override
    public boolean emailExist(String email) {
        return repo.findByEmail(email) != null;
    }

    @Override
    public boolean validateUser(String email, String password) {
        Users user = repo.findByEmail(email);
        if (user == null) {
            return false; // Email not found
        }
        // Compare the provided password with the hashed password from the DB
        return passwordEncoder.matches(password, user.getPassword());
    }
}
