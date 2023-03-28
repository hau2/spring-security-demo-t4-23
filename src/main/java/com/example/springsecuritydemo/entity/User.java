package com.example.springsecuritydemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "username",nullable = false)
    private String username;

    @Column(length = 128, nullable = false)
    private String password;

    private boolean isEnable;
    private String ipAddress;
    private Integer countFailLogin;
    private String lastTimeLogin;
}