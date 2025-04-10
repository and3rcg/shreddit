package com.example.shreddit.dto.response;

import com.example.shreddit.entity.User;
import com.example.shreddit.entity.UserRole;

public record UserResponseDTO(String username, UserRole role) {
    public UserResponseDTO(User user) {
        this(user.getUsername(), user.getRole());
    }
}
