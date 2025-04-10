package com.example.shreddit.dto.response;

import com.example.shreddit.entity.User;
import com.example.shreddit.entity.UserRole;

// this DTO is used to return basic information about a user (username, isStaff, karma)
// still wip
public record UserBasicResponseDTO(String username, UserRole role, int karma) {
    public UserBasicResponseDTO(User user) {
        this(
            user.getUsername(),
            user.getRole(),
            0  // TODO: calculate the user's karma, which is the score of all votes in their content (posts and comments)
        );
    }
}
