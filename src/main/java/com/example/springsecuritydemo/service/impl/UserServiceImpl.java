package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public void updateIsEnableByUsername(Boolean isEnable, String username) {
        userRepository.updateIsEnableByUsername(isEnable, username);
    }

    @Override
    public void updateCountFailLoginByUsername(int countFailLogin, String username) {
        userRepository.updateCountFailLoginByUsername(countFailLogin, username);
    }

    @Override
    public Integer getCountFailLoginByUsername(String username) {
        return userRepository.getCountFailLoginByUsername(username);
    }
}
