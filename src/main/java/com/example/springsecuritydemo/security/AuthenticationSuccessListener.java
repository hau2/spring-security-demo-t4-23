package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener
        implements
        ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletRequest request;

    @SneakyThrows
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = request.getParameter("username");
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            loginService.loginSuccess(request.getRemoteAddr(),username);
        } else {
            loginService.loginSuccess(xfHeader.split(",")[0], username);
        }
    }
}
