package com.example.springsecuritydemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "mail", nullable = false)
    private String mail;
    @Column(length = 128, nullable = false)
    private String password;
    private boolean isEnable;
    private String privateCode;
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime expiryDateCode;
}