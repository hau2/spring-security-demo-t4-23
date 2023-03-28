package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<String> findAllRoleByUserId(Integer userId) {
        return userRoleRepository.findAllRoleByUserId(userId);
    }
}
