package com.example.jobportal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobportal.model.CandidateProfile;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {

    Optional<CandidateProfile> findByUserId(Long userId);

    Optional<CandidateProfile> findByUserEmail(String email);
}