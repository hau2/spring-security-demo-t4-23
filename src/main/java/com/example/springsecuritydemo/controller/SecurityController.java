package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.payload.request.LoginRequest;
import com.example.springsecuritydemo.payload.response.CustomResponse;
import com.example.springsecuritydemo.payload.response.JwtResponse;
import com.example.springsecuritydemo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SecurityController {
    @Autowired
    AuthService authService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.signIn(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomResponse<>(200, jwtResponse));
    }
}
