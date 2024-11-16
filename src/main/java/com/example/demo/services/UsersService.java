package com.example.demo.services;

import com.example.demo.entity.Users;

public interface UsersService {
    public String addUser(Users user);
    public boolean emailExist(String email);
    public boolean validateUser(String email, String password);
}



