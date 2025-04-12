package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.response.UserMeResponseDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<UserMeResponseDTO> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserMeResponseDTO user = userService.getMe(userDetails);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateMe(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody UserCreateRequestDTO request) {
        try {
            UserResponseDTO user = userService.updateUser(userDetails, request);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity deleteMe(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();
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
