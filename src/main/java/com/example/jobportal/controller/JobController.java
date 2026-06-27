package com.example.jobportal.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.jobportal.Dto.PageResponse;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;
import com.example.jobportal.service.AuthService;
import com.example.jobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final AuthService authService;

    public JobController(
            JobService jobService,
            AuthService authService) {

        this.jobService = jobService;
        this.authService = authService;
    }

    // =========================
    // CREATE JOB
    // =========================
    @PostMapping
    @PreAuthorize("hasRole('RECRUITER')")
    public Job createJob(
            @RequestBody Job job,
            Principal principal) {

        User user =
                authService.findByEmail(
                        principal.getName());

        return jobService.createJob(job, user);
    }

    // =========================
    // GET JOB BY ID
    // =========================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public Job getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id);
    }

    // =========================
    // UPDATE JOB
    // =========================
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        return jobService.updateJob(id, job);
    }

    // =========================
    // DELETE JOB
    // =========================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public String deleteJob(
            @PathVariable Long id,
            Principal principal) {

        User user =
                authService.findByEmail(
                        principal.getName());

        return jobService.deleteJob(id, user);
    }

    // =========================
    // SEARCH JOBS
    // =========================
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public List<Job> searchJobs(
            @RequestParam String keyword) {

        return jobService.searchJobs(keyword);
    }

    // =========================
    // PAGINATION + SORTING
    // =========================
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public PageResponse<Job> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return jobService.getAllJobsPaged(
                page,
                size,
                sortBy);
    }

    // =========================
    // ADMIN - ALL JOBS
    // =========================
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Job> getAllJobsAdmin() {

        return jobService.getAllJobs();
    }

    // =========================
    // RECRUITER - MY JOBS
    // =========================
    @GetMapping("/my-jobs")
    @PreAuthorize("hasRole('RECRUITER')")
    public List<Job> getMyJobs(
            Principal principal) {

        User user =
                authService.findByEmail(
                        principal.getName());

        return jobService.getJobsByUser(user);
    }
}