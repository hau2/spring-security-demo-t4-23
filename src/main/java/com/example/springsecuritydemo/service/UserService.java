package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface UserService {
    Optional<User> findByMail(String mail);
    void updateIsEnableByMail(Boolean isEnable, String username);

    void sendMailResetPassword(String mail) throws MessagingException;
    User finByPrivateCode(String privateCode) throws UserNotFoundException, PrivateCodeHasExpired;
    void setNewPassword(ResetPasswordRequest request) throws PrivateCodeHasExpired, UserNotFoundException;
}
