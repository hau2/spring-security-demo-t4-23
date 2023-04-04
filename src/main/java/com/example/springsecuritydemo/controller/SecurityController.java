package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserAlreadyExistException;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.payload.request.ForgotPasswordRequest;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import com.example.springsecuritydemo.service.IUserService;
import com.example.springsecuritydemo.service.LoginService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("")
public class SecurityController {
    @Autowired
    IUserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

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

    @GetMapping("/request-reset-password")
    public ModelAndView requestResetPassword() {
        return new ModelAndView("request-reset-password", "forgotPass", new ForgotPasswordRequest(""));
    }

    @PostMapping("/request-reset-password")
    public ModelAndView resetPassword(@ModelAttribute("forgotPass") ForgotPasswordRequest request) throws MessagingException {
        userService.sendMailResetPassword(request.getMail());
        return new ModelAndView("request-reset-password", "forgotPass", new ForgotPasswordRequest(request.getMail()));
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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup-form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) throws UserAlreadyExistException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnable(true);

        User resultUser = userService.register(user);

        return "register-success";
    }
}
