package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ForgotPasswordRequest;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import com.example.springsecuritydemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("")
public class SecurityController {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }
    @GetMapping("/login")
    public ModelAndView authenticateUser(@RequestParam(required = false) Optional<Boolean> error,
                                         @RequestParam(required = false) Optional<Boolean> spam) {
        Boolean isError = error.orElse(false);
        Boolean isSpam = spam.orElse(false);

        if(isSpam) {
            return new ModelAndView("login", "spam_msg", "You have tried logging in too many times. Try again in a few minutes.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if(isError) {
                return new ModelAndView("login", "error_msg", "Invalid username and password.");
            }
        }
        return new ModelAndView("login");
    }

    @GetMapping("/user_blocked")
    public ModelAndView getUserBlockedPage(@RequestParam(required = false) Optional<String> mail) {
        String email = mail.orElse(null);
        if(email == null) {
            new ModelAndView("login");
        }
        return new ModelAndView("userBlocked", "forgotPass", new ForgotPasswordRequest(mail.get()));
    }

    @PostMapping("/request-reset-password")
    public ModelAndView resetPassword(@ModelAttribute("forgotPass") ForgotPasswordRequest request) throws MessagingException {
        userService.sendMailResetPassword(request.getMail());
        return new ModelAndView("userBlocked", "forgotPass", new ForgotPasswordRequest(request.getMail()));
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
