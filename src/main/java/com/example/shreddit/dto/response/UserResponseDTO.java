package com.example.shreddit.dto.response;

import com.example.shreddit.entity.User;

public record UserResponseDTO(String username, boolean isStaff) {
    public UserResponseDTO(User user) {
        this(user.getUsername(), user.isStaff());
    }
}
