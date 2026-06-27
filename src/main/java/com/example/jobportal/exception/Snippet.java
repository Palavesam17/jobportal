package com.example.jobportal.exception;

import com.example.jobportal.dao.JobRepository;
import com.example.jobportal.model.Job;

public class Snippet {
	
    private final JobRepository jobRepository;

    public Snippet(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

	
	public Job getJobById(Long id) {
	
	    return jobRepository
	            .findById(id)
	            .orElseThrow(
	                    () ->
	                            new ResourceNotFoundException(
	                                    "Job Not Found"
	                            )
	            );
	}
}

