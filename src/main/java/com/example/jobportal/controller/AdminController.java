package com.example.jobportal.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.model.Application;
import com.example.jobportal.model.Resume;
import com.example.jobportal.model.User;
import com.example.jobportal.service.AuthService;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.ResumeService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthService authService;
    private final ApplicationService applicationService;
    private final ResumeService resumeService;

    public AdminController(
            AuthService authService,
            ApplicationService applicationService,
            ResumeService resumeService) {

        this.authService = authService;
        this.applicationService = applicationService;
        this.resumeService = resumeService;
    }

    // =========================
    // GET ALL USERS
    // =========================
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

    // =========================
    // DELETE USER
    // =========================
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return authService.deleteUser(id);
    }

    // =========================
    // GET ALL APPLICATIONS
    // =========================
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // =========================
    // GET ALL RESUMES
    // =========================
    @GetMapping("/resumes")
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    // =========================
    // DELETE RESUME
    // =========================
    @DeleteMapping("/resumes/{id}")
    public String deleteResume(@PathVariable Long id) {
        return resumeService.deleteResume(id);
    }
}