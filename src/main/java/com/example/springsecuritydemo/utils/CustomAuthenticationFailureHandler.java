package com.example.springsecuritydemo.utils;

import com.example.springsecuritydemo.constants.Constants;
import com.example.springsecuritydemo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        int countFailLogin = userService.getCountFailLoginByUsername(username);
        System.out.println("username = " + username);
        System.out.println("countFailLogin = " + countFailLogin);

        if (!userService.findByUsername(username).isEnable() ||countFailLogin >= Constants.COUNT_LOCK_USER) {
            userService.updateIsEnableByUsername(false, username);
            userService.updateCountFailLoginByUsername(0, username);
            response.sendRedirect("blocked");

        } else {
            userService.updateCountFailLoginByUsername(countFailLogin+1, username);
            response.sendRedirect("login");
        }




    }
}
