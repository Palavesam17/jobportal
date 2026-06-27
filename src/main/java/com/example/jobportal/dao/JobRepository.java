package com.example.jobportal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;

public interface JobRepository
        extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String keyword);

    Page<Job> findAll(Pageable pageable);

    List<Job> findByCreatedById(Long id);

    List<Job> findByCreatedBy(User user);

    List<Job> findByCompanyContainingIgnoreCase(String company);

    List<Job> findByLocationContainingIgnoreCase(String location);
    
   

   
}