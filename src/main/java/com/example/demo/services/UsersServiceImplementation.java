package com.example.demo.services;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImplementation implements UsersService {

    @Autowired
    private UsersReporsitory repo;

    @Override
    public String addUser(Users user) {
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
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    @Override
    public String getRole(String email) {
        Users user = repo.findByEmail(email);
        if (user == null) {
            return "invalid";
        }
        return user.getRole();
    }

    @Override
    public Users getUser(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public void updateUser(Users users) {
        repo.save(users);
    }
}
