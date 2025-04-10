package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.request.LoginDTO;
import com.example.shreddit.dto.request.UserCreateRequestDTO;
import com.example.shreddit.dto.response.LoginResponseDTO;
import com.example.shreddit.dto.response.UserResponseDTO;
import com.example.shreddit.entity.User;
import com.example.shreddit.service.AuthService;
import com.example.shreddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request) {
        UsernamePasswordAuthenticationToken loginInput = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        Authentication auth = this.authenticationManager.authenticate(loginInput);
        System.out.println("xok");
        var token = authService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateRequestDTO request) {
        UserResponseDTO user = this.userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}
