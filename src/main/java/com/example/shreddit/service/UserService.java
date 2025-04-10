package com.example.shreddit.service;

import com.example.shreddit.dto.request.UserCreateRequestDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.entity.User;
import com.example.shreddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO createUser(UserCreateRequestDTO request) {
        if (!request.password().equals(request.rePassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        };
        User user = new User(request.username(), request.password(), request.email());
        repository.save(user);
        return new UserResponseDTO(user);
    }

    public UserDetails findUserByUsername(String username) {
        return (User) repository.findByUsername(username);
    }

    public void deleteUser(String username) {
        UserDetails user = findUserByUsername(username);
        repository.delete((User) user);
    }
}
