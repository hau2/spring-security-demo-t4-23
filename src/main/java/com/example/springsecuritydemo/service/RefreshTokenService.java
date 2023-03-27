package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
}
