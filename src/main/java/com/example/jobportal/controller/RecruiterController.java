package com.example.jobportal.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.model.Application;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;
import com.example.jobportal.service.ApplicationService;
import com.example.jobportal.service.AuthService;
import com.example.jobportal.service.JobService;

@RestController
@RequestMapping("/api/recruiter")
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterController {

    private final JobService jobService;
    private final AuthService authService;
    private final ApplicationService applicationService;

    public RecruiterController(
            JobService jobService,
            AuthService authService,
            ApplicationService applicationService) {

        this.jobService = jobService;
        this.authService = authService;
        this.applicationService = applicationService;
    }

    // =========================
    // CREATE JOB
    // =========================
    @PostMapping("/jobs")
    public Job createJob(
            @RequestBody Job job,
            Principal principal) {

        User user = authService.findByEmail(principal.getName());

        return jobService.createJob(job, user);
    }

    // =========================
    // MY JOBS
    // =========================
    @GetMapping("/jobs")
    public List<Job> myJobs(Principal principal) {

        User user = authService.findByEmail(principal.getName());

        return jobService.getJobsByUser(user);
    }

    // =========================
    // UPDATE JOB
    // =========================
    @PutMapping("/jobs/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        return jobService.updateJob(id, job);
    }

    // =========================
    // DELETE JOB
    // =========================
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(
            @PathVariable Long id,
            Principal principal) {

        User user = authService.findByEmail(principal.getName());

        return jobService.deleteJob(id, user);
    }

    // =========================
    // VIEW APPLICANTS FOR JOB
    // =========================
    @GetMapping("/jobs/{jobId}/applications")
    public List<Application> getApplicants(@PathVariable Long jobId) {
        return applicationService.getApplicants(jobId);
    }

    // =========================
    // UPDATE APPLICATION STATUS
    // =========================
    @PutMapping("/applications/{id}/status")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return applicationService.updateStatus(id, status);
    }
}