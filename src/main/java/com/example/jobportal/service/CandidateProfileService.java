package com.example.jobportal.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.jobportal.dao.CandidateProfileRepository;
import com.example.jobportal.model.CandidateProfile;


@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateProfileService(
            CandidateProfileRepository candidateProfileRepository) {

        this.candidateProfileRepository = candidateProfileRepository;
    }

    public CandidateProfile findByUserId(Long userId) {
        return candidateProfileRepository
                .findByUserId(userId)
                .orElse(null);
    }
    
    // =========================
    // CREATE PROFILE
    // =========================
    public CandidateProfile saveProfile(
            CandidateProfile profile) {

        if (profile == null) {
            throw new RuntimeException("Profile data is required");
        }

        return candidateProfileRepository.save(profile);
    }

    // =========================
    // GET PROFILE BY USER ID
    // =========================
    public CandidateProfile getProfileByUserId(
            Long userId) {

        return candidateProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Profile not found for user id: " + userId));
    }

    // =========================
    // UPDATE PROFILE
    // =========================
    public CandidateProfile updateProfile(
            Long userId,
            CandidateProfile updatedProfile) {

        CandidateProfile existing =
                candidateProfileRepository
                        .findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Profile not found"));

        if (updatedProfile.getFullName() != null) {
            existing.setFullName(
                    updatedProfile.getFullName());
        }

        if (updatedProfile.getPhone() != null) {
            existing.setPhone(
                    updatedProfile.getPhone());
        }

        if (updatedProfile.getLocation() != null) {
            existing.setLocation(
                    updatedProfile.getLocation());
        }

        if (updatedProfile.getSkills() != null) {
            existing.setSkills(
                    updatedProfile.getSkills());
        }

        if (updatedProfile.getEducation() != null) {
            existing.setEducation(
                    updatedProfile.getEducation());
        }

        if (updatedProfile.getExperience() != null) {
            existing.setExperience(
                    updatedProfile.getExperience());
        }

        if (updatedProfile.getProfileImage() != null) {
            existing.setProfileImage(
                    updatedProfile.getProfileImage());
        }

        return candidateProfileRepository.save(existing);
    }

    // =========================
    // DELETE PROFILE
    // =========================
    public String deleteProfile(Long id) {

        CandidateProfile profile =
                candidateProfileRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Profile not found"));

        candidateProfileRepository.delete(profile);

        return "Profile deleted successfully";
    }

    // =========================
    // GET ALL PROFILES
    // =========================
    public List<CandidateProfile> getAllProfiles() {
        return candidateProfileRepository.findAll();
    }
}