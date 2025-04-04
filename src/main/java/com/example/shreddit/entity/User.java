package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // password hash
    @Column(nullable = false, length = 60)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "is_staff", nullable = false)
    private boolean isStaff;

    // password encoder
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Empty constructor is a must for Hibernate
    protected User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.length() < 5) {
            throw new IllegalArgumentException("Username must be at least 5 characters long");
        } else if (username.length() > 20) {
            throw new IllegalArgumentException("Username must be at most 20 characters long");
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Username must only contain letters, numbers, and underscores");
        }
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String plainPassword) {
        if (plainPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        } else if (plainPassword.length() > 40) {
            throw new IllegalArgumentException("Password must be at most 40 characters long");
        } else if (!plainPassword.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Password must only contain letters, numbers, and underscores");
        }

        this.password = passwordEncoder.encode(plainPassword);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }
}
