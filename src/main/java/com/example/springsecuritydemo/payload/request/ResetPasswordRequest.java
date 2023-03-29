package com.example.springsecuritydemo.payload.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String newPassword;
}
