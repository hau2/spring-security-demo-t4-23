package com.example.springsecuritydemo.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CustomMailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String mail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(mail);
        helper.setSubject(subject);
        helper.setText(message, true);
        this.javaMailSender.send(mimeMessage);
    }
}
