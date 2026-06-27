package com.example.jobportal.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jobportal.Dto.LoginRequest;
import com.example.jobportal.Dto.LoginResponse;
import com.example.jobportal.Dto.RefreshTokenRequest;
import com.example.jobportal.Dto.RegisterRequest;
import com.example.jobportal.dao.UserRepository;
import com.example.jobportal.model.Role;
import com.example.jobportal.model.User;
import com.example.jobportal.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (request == null) {
            throw new RuntimeException("Request cannot be null");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        Role role;

        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid role. Use ADMIN, RECRUITER or CANDIDATE");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return "Registration successful";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole());

        String refreshToken = jwtService.generateRefreshToken(
                user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getRole().name()
        );
    }

    public LoginResponse refreshToken(
            RefreshTokenRequest request) {

        User user = userRepository
                .findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        String newAccessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole());

        String newRefreshToken = jwtService.generateRefreshToken(
                user.getEmail());

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken,
                user.getRole().name()
        );
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + email));
    }

    public User findByName(String name) {

        return userRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + name));
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {

        User user = findById(id);

        userRepository.delete(user);

        return "User deleted successfully";
    }
}