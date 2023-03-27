package com.example.springsecuritydemo.service;
import com.example.springsecuritydemo.payload.request.LoginRequest;
import com.example.springsecuritydemo.payload.response.JwtResponse;

public interface AuthService {
    JwtResponse signIn(LoginRequest loginRequest);
}
