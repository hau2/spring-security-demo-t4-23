package com.example.springsecuritydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/member_group")
    @PreAuthorize("hasRole('ROLE_MEMBER_GROUP') or hasRole('ROLE_ADMIN_GROUP') or hasRole('ROLE_ADMIN_SYSTEMS')")
    public String userAccess() {
        return "Member group Content.";
    }

    @GetMapping("/admin_group")
    @PreAuthorize("hasRole('ROLE_ADMIN_GROUP')")
    public String moderatorAccess() {
        return "ROLE_ADMIN_GROUP Board.";
    }

    @GetMapping("/admin_systems")
    @PreAuthorize("hasRole('ROLE_ADMIN_SYSTEMS')")
    public String adminAccess() {
        return "ROLE_ADMIN_SYSTEMS Board.";
    }
}
