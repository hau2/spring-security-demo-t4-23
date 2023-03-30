package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.service.LoginService;
import com.example.springsecuritydemo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;

public class CustomAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    @Autowired
    UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginService loginService;


    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String username = request.getParameter("username");


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

        if (loginService.isUserBlocked()) {
            errorMessage = "blocked user";
            userService.updateIsEnableByUsername(false, username);
            response.sendRedirect("user_blocked");
            return;
        }

        response.sendRedirect("/login");


    }
}
