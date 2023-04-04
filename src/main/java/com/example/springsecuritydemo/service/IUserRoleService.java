package com.example.springsecuritydemo.service;


import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.entity.User;

public interface IUserRoleService {
    void save(User resultUser, Role role);
}
