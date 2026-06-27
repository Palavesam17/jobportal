package com.example.jobportal.controller;

import org.springframework.web.bind.annotation.*;

import com.example.jobportal.Dto.LoginRequest;
import com.example.jobportal.Dto.LoginResponse;
import com.example.jobportal.Dto.RefreshTokenRequest;
import com.example.jobportal.Dto.RegisterRequest;
import com.example.jobportal.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(request);
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        return authService.deleteUser(id);
    }
}