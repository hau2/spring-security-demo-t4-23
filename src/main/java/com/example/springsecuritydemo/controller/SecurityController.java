package com.example.springsecuritydemo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
        System.out.println("authentication = " + authentication);
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
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

    @GetMapping("blocked")
    public String getBlockedPage() {
        return "blocked";
    }
}
