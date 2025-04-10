package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.request.UserCreateRequestDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
        // TODO: adicionar rotina de pegar o usuário executante aqui
        return "Me";
    }

    @PutMapping("/me")
    public String updateMe() {
        // TODO: adicionar rotina de pegar o usuário executante aqui
        return "Me updated";
    }

    @DeleteMapping
    public String deleteMe() {
        // TODO: adicionar rotina de pegar o usuário executante aqui
        return "Me deleted";
    }

    @DeleteMapping("/profile/{username}")
    public String deleteByUsername(@PathVariable String username) {
        // checar se o usuário executante tem role admin
        return "User " + username + " deleted";
    }

    @GetMapping("/profile/{username}")
    public UserDetails getByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }
}
