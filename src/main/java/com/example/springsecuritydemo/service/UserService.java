package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface UserService {
    void sendMailResetPassword(String mail) throws MessagingException;
    User finByPrivateCode(String privateCode) throws UserNotFoundException;
}
