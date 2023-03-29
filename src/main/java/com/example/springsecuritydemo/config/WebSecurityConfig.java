package com.example.springsecuritydemo.config;

//import com.example.springsecuritydemo.jwt.AuthTokenFilter;
import com.example.springsecuritydemo.service.UserDetailsServiceImpl;
import com.example.springsecuritydemo.security.CustomAuthenticationFailureHandler;
import com.example.springsecuritydemo.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    https://www.baeldung.com/spring-security-exceptions
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/user_blocked")
                .permitAll()
                .requestMatchers("/ip_blocked")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .failureHandler(authenticationFailureHandler())
                .successHandler(authenticationSuccessHandler());
//                .and()
//                .formLogin()
//                .usernameParameter("username")
//                .loginPage("/login")
//                .permitAll()
//                .failureHandler(authenticationFailureHandler());

        return http.build();
    }

    public static void main(String[] args) {
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        System.out.println(webSecurityConfig.passwordEncoder().encode("123456"));
    }
}
