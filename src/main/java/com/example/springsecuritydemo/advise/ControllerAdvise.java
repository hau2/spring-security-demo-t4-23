package com.example.springsecuritydemo.advise;

import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = {PrivateCodeHasExpired.class, UserNotFoundException.class})
    public ModelAndView handleException() {
        return new ModelAndView("/404");
    }
}
