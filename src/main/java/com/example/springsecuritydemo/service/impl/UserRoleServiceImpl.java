package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.entity.UserRole;
import com.example.springsecuritydemo.repository.UserRoleRepository;
import com.example.springsecuritydemo.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void save(User user, Role role) {
        userRoleRepository.save(new UserRole(user, role));
    }
}
