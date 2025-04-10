package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
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

    @Column(name = "role", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("USER"));
        }
    }

    // password encoder
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Empty constructor is a must for Hibernate
    protected User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        this.setRole(UserRole.USER);
    }

    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.email = email;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
