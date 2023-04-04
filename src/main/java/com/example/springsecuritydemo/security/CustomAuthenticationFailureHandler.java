package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.service.LoginService;
import com.example.springsecuritydemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler
        implements AuthenticationFailureHandler {
    @Autowired
    UserService userService;
    @Autowired
    private LoginService loginService;

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) {
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
            response.sendRedirect("/login?spam=true");
            return;
        }

        User user = userService.findByMail(mail).orElse(null);
        if (user == null) {
            response.sendRedirect("/login?error=true");
            return;
        }

        if (user.isEnable()) {
            if (!loginService.isUserBlockedInCache()) {
                response.sendRedirect("/login?error=true");
                return;
            }
            userService.updateIsEnableByMail(false, mail);
            userService.sendMailResetPassword(mail);
        }

        response.sendRedirect("/user_blocked?mail=" + mail);
    }
}
