package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import jakarta.mail.MessagingException;

public interface UserService {
    void updateIsEnableByUsername(Boolean isEnable,String username);
    void updateCountFailLoginByUsername(int countFailLogin, String username);
    Integer getCountFailLoginByUsername(String username);
    void sendMailResetPassword(String mail) throws MessagingException;
    User finByPrivateCode(String privateCode) throws UserNotFoundException, PrivateCodeHasExpired;
    void setNewPassword(ResetPasswordRequest request) throws PrivateCodeHasExpired, UserNotFoundException;
}
