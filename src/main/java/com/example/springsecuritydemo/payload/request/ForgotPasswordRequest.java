package com.example.springsecuritydemo.payload.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String mail;
}
