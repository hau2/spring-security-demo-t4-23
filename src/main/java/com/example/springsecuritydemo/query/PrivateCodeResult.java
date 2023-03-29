package com.example.springsecuritydemo.query;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;

public interface PrivateCodeResult {
    @Value("#{target.private_code}")
    String getPrivateCode();
    @Value("#{target.expiry_date_code}")
    LocalDateTime getExpiryDate();
}
