package com.example.jobportal.Dto;

import jakarta.validation.constraints.NotBlank;

public class CandidateProfileDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Education is required")
    private String education;

    @NotBlank(message = "Experience is required")
    private String experience;

    private String profileImage;

    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================
    public CandidateProfileDto() {
    }

    // =========================
    // PARAMETERIZED CONSTRUCTOR
    // =========================
    public CandidateProfileDto(String fullName,
                               String phone,
                               String location,
                               String skills,
                               String education,
                               String experience,
                               String profileImage) {
        this.fullName = fullName;
        this.phone = phone;
        this.location = location;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.profileImage = profileImage;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}