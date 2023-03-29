package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.query.PrivateCodeResult;
import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.service.UserService;
import com.example.springsecuritydemo.utils.CustomMailSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Value("${demo.app.verifyCodeDurationMinutes}")
    private Integer verifyCodeDurationMinutes;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomMailSender mailSender;

    @Override
    public void sendMailResetPassword(String mail) {
        // Check private code in the database if private code has existed -> not generate a new private code
        PrivateCodeResult privateCodeResult = userRepository.findPrivateCodeByMail(mail);
        String privateCode = privateCodeResult.getPrivateCode();
        LocalDateTime timeExpired = privateCodeResult.getExpiryDate();
        System.err.println("timeExpired.compareTo(Instant.now()): "+ timeExpired);
        System.err.println("NOW: " + LocalDateTime.now());

        if (privateCode == null || timeExpired == null || timeExpired.isBefore(LocalDateTime.now())) {
            privateCode = UUID.randomUUID().toString().replace("-", "");
            // Expiry date code will expire after 2 minute from now
            timeExpired = LocalDateTime.now().plusSeconds(verifyCodeDurationMinutes);
            userRepository.setPrivateCodeForUser(privateCode, timeExpired, mail);
        }

        String finalPrivateCode = privateCode;
        Thread t = new Thread(() -> {
            try {
                sendCodeToUser(mail, finalPrivateCode);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();
    }

    @Override
    public User finByPrivateCode(String privateCode) throws UserNotFoundException {
        return userRepository.finByPrivateCode(privateCode).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void sendCodeToUser(String mail, String code) throws MessagingException {
        mailSender.send(mail, "RESET_PASSWORD", "localhost:8080/reset?code=" + code);
    }

    private PrivateCodeResult findPrivateCodeByMail(String mail) {
        return userRepository.findPrivateCodeByMail(mail);
    }

    public static void main(String[] args) {
        Instant timeExpired = Instant.parse("2023-03-29T02:26:26.211283Z");
        System.out.println(Instant.now());
        System.out.println(Instant.now().plusMillis(30000));
        System.out.println(timeExpired.compareTo(Instant.now()) < 0);
    }
}
