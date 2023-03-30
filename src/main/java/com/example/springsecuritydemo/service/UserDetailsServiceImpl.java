package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.model.ERole;
import com.example.springsecuritydemo.repository.RoleRepository;
import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private LoginService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    RoleRepository roleRepository;

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (roles != null) {
            for (String roleName : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
                grantedAuthorities.add(authority);
            }
        }

        return grantedAuthorities;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isIPAddressBlocked()) {
            throw new RuntimeException("blocked");
        }

//        try {
//            User user = userRepository.findByUsername(username);
//
//            if (user == null) {
//                return new org.springframework.security.core.userdetails.User(
//                        " ", " ", true, true, true, true,
//                        getAuthorities(Collections.singletonList((ERole.ROLE_ANONYMOUS.toString()))));
//            }
//
//            List<String> roles = userRoleRepository.findAllRoleByUserId(user.getId());
//
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.isEnable(),
//                    true, true, true,
//                    getAuthorities(roles));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

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
