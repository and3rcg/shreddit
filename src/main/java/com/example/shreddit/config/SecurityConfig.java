package com.example.shreddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/**").permitAll() // Public endpoints
                        .requestMatchers("/api/private/**").authenticated() // Requires login
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
//                        .anyRequest().authenticated() // All others require auth
//                .formLogin(form -> form
//                        .loginPage("/login") // Custom login page
//                        .permitAll()
                );

        return http.build();
    }
}