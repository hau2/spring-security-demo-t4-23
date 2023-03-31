package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.model.ERole;
import com.example.springsecuritydemo.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            loginAttemptService.loginFailedIPAddress(request.getRemoteAddr());
        } else {
            loginAttemptService.loginFailedIPAddress(xfHeader.split(",")[0]);
        }

        System.out.println(request.getParameter("username"));
        System.out.println(event.getAuthentication());
        if (!event.getAuthentication().getAuthorities().isEmpty()) {
            String username = request.getParameter("username");
            System.out.println("username fail = "  + username);
            loginAttemptService.loginFailedUser(username);
        }


    }
}
