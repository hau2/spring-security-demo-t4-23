package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.service.LoginService;
import com.example.springsecuritydemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    @Autowired
    UserService userService;
    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String mail = request.getParameter("mail");


        // not found username > 3 lan -> lock ip
        String errorMessage = "badCredentials";

        if (loginService.isIPAddressBlocked()) {
            errorMessage = "blocked ip address";
        }
        if (exception.getMessage().equalsIgnoreCase("blocked ip address")) {
            errorMessage = "blocked ip address";
        }

        if (errorMessage.contains("blocked ip address")) {
            System.out.println("blocked ip roiiiiiii");
            response.sendRedirect("ip_blocked");
            return;
        }



        try {
            if (userService.findByMail(mail).get().isEnable()) {
                if (loginService.isUserBlocked()) {
                    userService.updateIsEnableByMail(false, mail);
                    userService.sendMailResetPassword(mail);
                } else {
                    response.sendRedirect("/login");
                }
            }
            response.sendRedirect("user_blocked");

        } catch (UserNotFoundException | MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
