package com.example.springsecuritydemo.service;

import java.util.List;

public interface UserRoleService {
    List<String> findAllRoleByUserId(Integer userId);
}
