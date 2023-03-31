package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ForgotPasswordRequest;
import com.example.springsecuritydemo.payload.request.ResendMailRequest;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import com.example.springsecuritydemo.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/user_blocked")
    public String getUserBlockedPage(Model model) {
        model.addAttribute("forgotPass", new ForgotPasswordRequest("leconghau095@gmail.com"));
        return "userBlocked";
    }

    @GetMapping("/ip_blocked")
    public String getIPBlockedPage(Model model) {
        model.addAttribute("username", "Username");
        return "ipBlocked";
    }

    @GetMapping("/request-reset-password")
    public ModelAndView requestResetPassword() {
        return new ModelAndView("request-reset-password","forgotPass", new ForgotPasswordRequest("leconghau095@gmail.com"));
    }

    @PostMapping("/request-reset-password")
    public String resetPassword(@ModelAttribute("forgotPass") ForgotPasswordRequest request) throws MessagingException {
        userService.sendMailResetPassword(request.getMail());
        return "index";
    }

    @GetMapping("/reset")
    public ModelAndView showFromResetPassword(@RequestParam(name = "code") String privateCode) throws UserNotFoundException, PrivateCodeHasExpired {
        userService.finByPrivateCode(privateCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("reset-password");
        modelAndView.addObject("resetPassRequest", new ResetPasswordRequest(privateCode));

        return modelAndView;
    }
    @PostMapping("/reset-password")
    public String doResetPassword(@ModelAttribute("resetPassRequest") ResetPasswordRequest request) throws PrivateCodeHasExpired, UserNotFoundException {
        userService.setNewPassword(request);
        return "redirect:/";
    }
}
