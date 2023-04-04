package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.model.ERole;
import com.example.springsecuritydemo.repository.RoleRepository;
import com.example.springsecuritydemo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleRepository roleRepository;

}
