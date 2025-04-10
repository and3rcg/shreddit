package com.example.shreddit.dto.request;

import com.example.shreddit.entity.UserRole;

// DTO for user creation
// will check for matching passwords in the controller
public record UserCreateRequestDTO(String username, String email, String password, String rePassword, UserRole role) {
    public UserCreateRequestDTO(String username, String email, String password, String rePassword) {
        this(username, email, password, rePassword, UserRole.USER);
    }
}
