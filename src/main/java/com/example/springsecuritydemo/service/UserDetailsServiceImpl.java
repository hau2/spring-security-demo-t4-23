package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<String> roles = userRoleRepository.findAllRoleByUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for (String roleName : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
                authorities.add(authority);
            }
        }

        return UserDetailsImpl.build(user, authorities);
    }

}
