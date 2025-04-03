package com.example.shreddit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;

    // TODO: implementar unique constraint para username e para e-mail
    private String username;
    private String password;
    private String email;
    private boolean isStaff;

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

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        // TODO: implementar criptografia de senha com salt
        this.password = password;
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
