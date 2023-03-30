package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsername(String username);
    void updateIsEnableByUsername(Boolean isEnable,String username);
    void updateCountFailLoginByUsername(int countFailLogin, String username);
    Integer getCountFailLoginByUsername(String username);
}
