package com.example.shreddit.controller.v1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    @GetMapping("/me")
    public String getMe() {
        return "Me";
    }

    @PutMapping("/me")
    public String updateMe() {
        return "Me updated";
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
