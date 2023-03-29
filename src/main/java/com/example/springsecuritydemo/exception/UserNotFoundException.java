package com.example.springsecuritydemo.exception;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String s) {
        super(s);
        log.error("User has privateCode is {s} not found in the database");
    }
}
