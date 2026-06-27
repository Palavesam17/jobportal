package com.example.jobportal.Dto;

import com.example.jobportal.model.ApplicationStatus;

import jakarta.validation.constraints.NotNull;

public class ApplicationDto {

    @NotNull(message = "Job ID required")
    private Long jobId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    public ApplicationDto() {
    }

    public ApplicationDto(Long jobId, ApplicationStatus status) {
        this.jobId = jobId;
        this.status = status;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}