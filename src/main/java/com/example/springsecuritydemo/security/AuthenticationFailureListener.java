package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.service.LoginService;
import com.example.springsecuritydemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent>{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        System.out.println("AuthenticationFailureBadCredentialsEvent");
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            loginService.loginFailedIPAddress(request.getRemoteAddr());
        } else {
            loginService.loginFailedIPAddress(xfHeader.split(",")[0]);
        }

        String mail = request.getParameter("mail");
        System.out.println("find by mail = " + userService.findByMail(mail));
        if (userService.findByMail(mail).isPresent()) {
            System.out.println("mail fail = "  + mail);
            loginService.loginFailedUser(mail);
        }
    }
}
