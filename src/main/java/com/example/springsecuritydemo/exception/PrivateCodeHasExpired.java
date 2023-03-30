package com.example.springsecuritydemo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrivateCodeHasExpired extends Exception {
    public PrivateCodeHasExpired(String s) {
        super(s);
        log.error("Private code has expired");
    }
}
