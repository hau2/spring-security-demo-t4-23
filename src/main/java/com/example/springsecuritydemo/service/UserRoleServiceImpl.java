package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    UserRoleRepository userRoleRepository;
}
