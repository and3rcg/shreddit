package com.example.shreddit.service;

import com.example.shreddit.dto.request.UserCreateRequestDTO;
import com.example.shreddit.dto.response.UserMeResponseDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.entity.User;
import com.example.shreddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public User findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void deleteUser(String username) {
        User user = findUserByUsername(username);
        repository.delete(user);
    }
    
    // validations in this method: username and email cannot be null or taken by other users
    // password and rePassword must match
    public UserResponseDTO updateUser(UserDetails userDetails, UserCreateRequestDTO request) 
    throws IllegalArgumentException {
        User user = findUserByUsername(userDetails.getUsername());
        if (request.username().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null");
        } else if (!request.username().equals(user.getUsername())) {
            User otherUser = repository.findByUsername(request.username());
            if (otherUser != null) {
                throw new IllegalArgumentException("Username is already taken");
            }
            user.setUsername(request.username());
        }

        if (request.password().isEmpty() || request.rePassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null");
        } else if (!request.password().equals(request.rePassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (request.email().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        } else if (!request.email().equals(user.getEmail())) {
            User otherUser = repository.findByEmail(request.email());
            if (otherUser != null) {
                throw new IllegalArgumentException("Email is already taken");
            }
        }

        user.setUsername(request.username());
        user.setEmail(request.email());

        repository.save(user);
        return new UserResponseDTO(user);
    }

    public void deleteMe(UserDetails userDetails) {
        User user = findUserByUsername(userDetails.getUsername());
        repository.delete(user);
    }

    public UserMeResponseDTO getMe(UserDetails userDetails) {
        User user = findUserByUsername(userDetails.getUsername());
        return new UserMeResponseDTO(user);
    }
}
