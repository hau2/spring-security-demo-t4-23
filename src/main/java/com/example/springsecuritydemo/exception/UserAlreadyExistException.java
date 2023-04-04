package com.example.springsecuritydemo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String s) {
        super(s);
        log.error("There is an account with that mail address: "  + s);
    }
}
