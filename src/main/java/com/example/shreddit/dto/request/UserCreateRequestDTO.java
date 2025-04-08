package com.example.shreddit.dto.request;

// DTO for user creation
// will check for matching passwords in the controller
public record UserCreateRequestDTO(String username, String email, String password, String rePassword) {}
