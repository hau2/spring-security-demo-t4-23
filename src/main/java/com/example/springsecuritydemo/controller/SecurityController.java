package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ForgotPasswordRequest;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import com.example.springsecuritydemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

@Controller
@RequestMapping("")
public class SecurityController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
    @GetMapping("/login")
    public String authenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/request-reset-password")
    public ModelAndView requestResetPassword() {
        return new ModelAndView("request-reset-password","forgotPass", new ForgotPasswordRequest());
    }

    @PostMapping("/request-reset-password")
    public String resetPassword(@ModelAttribute("forgotPass") ForgotPasswordRequest request) throws MessagingException {
        userService.sendMailResetPassword(request.getMail());
        return "index";
    }

    @GetMapping("/reset")
    public ModelAndView showFromResetPassword(@RequestParam(name = "code") String privateCode) {
        try {
            userService.finByPrivateCode(privateCode);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return new ModelAndView("/404");
        }
        return new ModelAndView("reset-password","resetPassRequest", new ResetPasswordRequest());
    }
    @PostMapping("/reset")
    public ModelAndView doResetPassword(@ModelAttribute("resetPassRequest") ResetPasswordRequest request) {

        return new ModelAndView("reset-password","resetPassRequest", new ResetPasswordRequest());
    }

    @GetMapping("/get-ip")
    public String getIp() throws UnknownHostException {
        try {
            InetAddress myIp = InetAddress.getLocalHost();
           System.out.println(myIp);
           System.out.println(myIp.getHostAddress());
           System.out.println(myIp.getHostName());
       } catch (UnknownHostException e) {
           throw new RuntimeException(e);
       }

       return InetAddress.getLocalHost().getHostAddress();
    }
}
