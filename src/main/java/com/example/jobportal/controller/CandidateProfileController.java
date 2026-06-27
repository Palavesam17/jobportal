package com.example.jobportal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.model.CandidateProfile;
import com.example.jobportal.model.User;
import com.example.jobportal.service.AuthService;
import com.example.jobportal.service.CandidateProfileService;

import java.security.Principal;

@RestController
@RequestMapping("/api/candidate-profile")
public class CandidateProfileController {

    private final CandidateProfileService profileService;
    private final AuthService authService;

    public CandidateProfileController(
            CandidateProfileService profileService,
            AuthService authService) {

        this.profileService = profileService;
        this.authService = authService;
    }

    // =========================
    // CREATE PROFILE
    // =========================
    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateProfile createProfile(
            @RequestBody CandidateProfile profile,
            Principal principal) {

        String email = principal.getName();

        User user = authService.findByEmail(email);

        // attach user
        profile.setUser(user);

        // prevent duplicate profile
        CandidateProfile existing =
                profileService.findByUserId(user.getId());

        if (existing != null) {
            throw new RuntimeException(
                    "Profile already exists. Use update API.");
        }

        return profileService.saveProfile(profile);
    }

    // =========================
    // GET MY PROFILE
    // =========================
    @GetMapping("/me")
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateProfile getMyProfile(
            Principal principal) {

        String email = principal.getName();

        User user = authService.findByEmail(email);

        return profileService.getProfileByUserId(user.getId());
    }

    // =========================
    // UPDATE PROFILE
    // =========================
    @PutMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateProfile updateProfile(
            @RequestBody CandidateProfile profile,
            Principal principal) {

        String email = principal.getName();

        User user = authService.findByEmail(email);

        return profileService.updateProfile(
                user.getId(),
                profile);
    }

    // =========================
    // DELETE PROFILE
    // =========================
    @DeleteMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public String deleteProfile(
            Principal principal) {

        String email = principal.getName();

        User user = authService.findByEmail(email);

        CandidateProfile existing =
                profileService.getProfileByUserId(user.getId());

        if (existing == null) {
            throw new RuntimeException("Profile not found");
        }

        profileService.deleteProfile(existing.getId());

        return "Profile deleted successfully";
    }
}