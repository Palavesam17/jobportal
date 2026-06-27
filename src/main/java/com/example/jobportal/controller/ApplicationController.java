package com.example.jobportal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.model.Application;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.AuthService;
import com.example.jobportal.service.JobService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final AuthService authService;
    private final JobService jobService;

    public ApplicationController(
            ApplicationService applicationService,
            AuthService authService,
            JobService jobService) {

        this.applicationService = applicationService;
        this.authService = authService;
        this.jobService = jobService;
    }

    // =========================
    // APPLY JOB (CANDIDATE ONLY)
    // =========================
    @PostMapping("/apply/{jobId}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String applyJob(
            @PathVariable Long jobId,
            Principal principal) {

        return applicationService.applyJob(
                jobId,
                principal.getName());
    }

    // =========================
    // MY APPLICATIONS (CANDIDATE)
    // =========================
    @GetMapping("/my")
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<Application> myApplications(
            Principal principal) {

        return applicationService.getMyApplications(
                principal.getName());
    }

    // =========================
    // APPLICANTS FOR JOB
    // =========================
    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public List<Application> applicants(
            @PathVariable Long jobId,
            Principal principal) {

        User user = authService.findByEmail(
                principal.getName());

        Job job = jobService.getJobById(jobId);

        if ("RECRUITER".equals(user.getRole().name())) {

            if (!job.getCreatedBy().getId()
                    .equals(user.getId())) {

                throw new RuntimeException(
                        "Not authorized for this job");
            }
        }

        return applicationService.getApplicants(jobId);
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @PutMapping("/{applicationId}/status")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public Application updateStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        if (status == null || status.trim().isEmpty()) {
            throw new RuntimeException(
                    "Status cannot be empty");
        }

        return applicationService.updateStatus(
                applicationId,
                status);
    }
}