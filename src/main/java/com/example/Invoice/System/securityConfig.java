package com.example.Invoice.System;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for development and testing
                .csrf().disable()

                // Allow public access to /addUser and /login, and secure all other endpoints
                .authorizeRequests()
                .requestMatchers("/addUser", "/loginUser").permitAll()
                .anyRequest().authenticated()

                // Use basic authentication (can be replaced with form login or JWT)
                .and()
                .httpBasic();

        return http.build();
    }

    // Password encoding bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}