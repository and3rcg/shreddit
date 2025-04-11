package com.example.shreddit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    // important: the order of the request matchers matter. make sure to put the more specific patterns at the top, and
    // keep wildcards at the bottom
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints
                .requestMatchers(new AntPathRequestMatcher("/api/auth/**", "POST")).permitAll()

                // Users endpoints
                .requestMatchers(new AntPathRequestMatcher("/api/v1/users/me", "GET")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/users/me", "PUT")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/users/profile/**", "GET")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/users/profile/**", "DELETE")).hasRole("ADMIN")

                // Posts endpoints
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts", "POST")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", "PUT")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", "DELETE")).hasRole("ADMIN")

                // Comments endpoints
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**/comments", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**/comments", "POST")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**/comments", "PUT")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**/comments", "DELETE")).authenticated()

                // Catch-all
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}