package com.example.jobportal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.jobportal.Dto.PageResponse;
import com.example.jobportal.dao.JobRepository;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.Role;
import com.example.jobportal.model.User;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // =========================
    // CREATE JOB
    // =========================
    public Job createJob(Job job, User user) {

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        job.setCreatedBy(user);

        return jobRepository.save(job);
    }

    // =========================
    // GET RECRUITER JOBS
    // =========================
    public List<Job> getJobsByUser(User user) {

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return jobRepository.findByCreatedBy(user);
    }

    // =========================
    // GET ALL JOBS
    // =========================
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // =========================
    // GET JOB BY ID
    // =========================
    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found with id: " + id));
    }

    // =========================
    // UPDATE JOB
    // =========================
    public Job updateJob(Long id, Job updatedJob) {

        Job job = getJobById(id);

        if (updatedJob.getTitle() != null
                && !updatedJob.getTitle().trim().isEmpty()) {
            job.setTitle(updatedJob.getTitle());
        }

        if (updatedJob.getCompany() != null
                && !updatedJob.getCompany().trim().isEmpty()) {
            job.setCompany(updatedJob.getCompany());
        }

        if (updatedJob.getLocation() != null
                && !updatedJob.getLocation().trim().isEmpty()) {
            job.setLocation(updatedJob.getLocation());
        }

        if (updatedJob.getDescription() != null
                && !updatedJob.getDescription().trim().isEmpty()) {
            job.setDescription(updatedJob.getDescription());
        }

        if (updatedJob.getSalary() != null) {
            job.setSalary(updatedJob.getSalary());
        }

        return jobRepository.save(job);
    }

    // =========================
    // DELETE JOB
    // =========================
    public String deleteJob(Long jobId, User user) {

        Job job = getJobById(jobId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Role role = user.getRole();

        // ADMIN CAN DELETE ANY JOB
        if (role == Role.ADMIN) {

            jobRepository.delete(job);

            return "Job deleted successfully by admin";
        }

        // RECRUITER CAN DELETE ONLY OWN JOB
        if (role == Role.RECRUITER) {

            if (job.getCreatedBy() != null
                    && job.getCreatedBy().getId().equals(user.getId())) {

                jobRepository.delete(job);

                return "Job deleted successfully";
            }

            throw new RuntimeException("You can delete only your own jobs");
        }

        throw new RuntimeException("Unauthorized");
    }

    // =========================
    // SEARCH JOBS
    // =========================
    public List<Job> searchJobs(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return jobRepository.findAll();
        }

        return jobRepository.findByTitleContainingIgnoreCase(
                keyword.trim());
    }

    // =========================
    // PAGINATION + SORTING
    // =========================
    public PageResponse<Job> getAllJobsPaged(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Job> jobPage = jobRepository.findAll(pageable);

        return new PageResponse<>(
                jobPage.getContent(),
                jobPage.getNumber(),
                jobPage.getSize(),
                jobPage.getTotalElements(),
                jobPage.getTotalPages(),
                jobPage.isLast()
        );
    }
}