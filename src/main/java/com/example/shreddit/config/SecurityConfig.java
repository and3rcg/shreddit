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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/**/posts").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/**/posts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "api/**/posts").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "api/**/posts/**/comments").permitAll()
                    .requestMatchers(HttpMethod.POST, "api/**/posts/**/comments").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "api/**/posts/**/comments").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "api/**/posts/**/comments").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "api/**/posts/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/**/posts/**").hasRole("ADMIN")

                    .requestMatchers(HttpMethod.GET, "/api/**/users/profile/**").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/api/**/users/profile/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/**/users/me").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/api/**/users/me").hasRole("USER")
                    .anyRequest().authenticated()
                    // TODO: cadastrar o resto dos endpoints. isso já é o bastante para a versão 0.1
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