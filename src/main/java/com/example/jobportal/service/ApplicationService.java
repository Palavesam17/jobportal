package com.example.jobportal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jobportal.dao.ApplicationRepository;
import com.example.jobportal.dao.JobRepository;
import com.example.jobportal.dao.UserRepository;
import com.example.jobportal.model.Application;
import com.example.jobportal.model.ApplicationStatus;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {

        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    // =========================
    // APPLY JOB
    // =========================
    public String applyJob(Long jobId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (applicationRepository.existsByUserAndJob(user, job)) {
            return "You have already applied for this job";
        }

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedDate(LocalDate.now());

        applicationRepository.save(application);

        return "Application submitted successfully";
    }

    // =========================
    // MY APPLICATIONS
    // =========================
    public List<Application> getMyApplications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return applicationRepository.findByUser(user);
    }

    // =========================
    // JOB APPLICANTS
    // =========================
    public List<Application> getApplicants(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        return applicationRepository.findByJobId(job.getId());
    }

    // =========================
    // UPDATE APPLICATION STATUS
    // =========================
    public Application updateStatus(
            Long applicationId,
            String status) {

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        try {
            ApplicationStatus applicationStatus =
                    ApplicationStatus.valueOf(status.toUpperCase());

            application.setStatus(applicationStatus);

            return applicationRepository.save(application);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Invalid status. Use PENDING, SHORTLISTED, REJECTED or HIRED");
        }
    }

    // =========================
    // GET APPLICATION BY ID
    // =========================
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));
    }

    // =========================
    // ALL APPLICATIONS (ADMIN)
    // =========================
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}