package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.request.UserCreateRequestDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    private final UserService userService;

    @Autowired
    public UserControllerV1(UserService service) {
        this.userService = service;
    }

    @GetMapping("/me")
    public String getMe() {
        return "Me";
    }

    @PutMapping("/me")
    public String updateMe() {
        return "Me updated";
    }

    @PostMapping("/create")
    public UserResponseDTO create(@RequestBody UserCreateRequestDTO request) {
        return this.userService.createUser(request);
    }

    @DeleteMapping
    public String deleteMe() {
        return "Me deleted";
    }

    @DeleteMapping("/{username}")
    public String deleteByUsername(@PathVariable String username) {
        // checar se o usuário executante é staff
        return "User " + username + " deleted";
    }
}
