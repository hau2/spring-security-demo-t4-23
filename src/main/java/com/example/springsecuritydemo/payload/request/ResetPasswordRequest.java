package com.example.springsecuritydemo.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequest {
    private String privateCode;
    private String newPassword;
    public ResetPasswordRequest(String privateCode) {
        this.privateCode = privateCode;
    }
}
