package com.example.springsecuritydemo.service;

public interface UserService {
    void updateIsEnableByUsername(Boolean isEnable,String username);
    void updateCountFailLoginByUsername(int countFailLogin, String username);
    Integer getCountFailLoginByUsername(String username);
}
