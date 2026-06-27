package com.example.jobportal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobportal.model.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    boolean existsByEmail(String email);
    
    Optional<User> findByRefreshToken(String refreshToken);
}