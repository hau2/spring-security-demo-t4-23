package com.example.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("modals")
public class ModalController {

    @GetMapping("blocked")
    public String modal1() {
        return "blocked";
    }
}
