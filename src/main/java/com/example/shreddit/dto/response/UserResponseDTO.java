package com.example.shreddit.dto.response;

import com.example.shreddit.entity.User;
import com.example.shreddit.entity.UserRole;

public record UserResponseDTO(String username, String email, UserRole role) {
    public UserResponseDTO(User user) {
        this(user.getUsername(), user.getEmail(), user.getRole());
    }
}
