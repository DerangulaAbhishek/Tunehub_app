package com.example.demo.services;

import com.example.demo.entity.Users;

public interface UsersService {
    String addUser(Users user);
    boolean emailExist(String email);
    boolean validateUser(String email, String password);
    String getRole(String email);
    Users getUser(String email);
    void updateUser(Users users);
}
