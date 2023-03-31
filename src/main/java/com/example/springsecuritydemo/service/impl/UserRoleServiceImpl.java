package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.repository.UserRoleRepository;
import com.example.springsecuritydemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
}
