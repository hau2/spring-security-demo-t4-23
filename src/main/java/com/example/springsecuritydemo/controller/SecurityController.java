package com.example.springsecuritydemo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping("")
public class SecurityController {

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

    @GetMapping("/user_blocked")
    public String getUserBlockedPage(Model model) {
        model.addAttribute("username", "Username");
        return "userBlocked";
    }

    @GetMapping("/ip_blocked")
    public String getIPBlockedPage(Model model) {
        model.addAttribute("username", "Username");
        return "ipBlocked";
    }


}
