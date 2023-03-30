package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String mail;
    @JsonIgnore
    private String password;
    private Boolean isEnable;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String mail, String password,
                           Collection<? extends GrantedAuthority> authorities, Boolean isEnable) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.authorities = authorities;
        this.isEnable = isEnable;
    }

    public static UserDetailsImpl build(User user, List<GrantedAuthority> authorities) {
        return new UserDetailsImpl(
                user.getId(),
                user.getMail(),
                user.getPassword(),
                authorities,
                user.isEnable());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
