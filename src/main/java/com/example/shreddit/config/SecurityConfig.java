package com.example.shreddit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf().disable()
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
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