package com.example.jobportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jobportal.model.Application;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.User;

@Repository
public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    List<Application> findByUser(User user);

    List<Application> findByJobId(Long jobId);

    List<Application> findByJob(Job job);

    boolean existsByUserAndJob(User user, Job job);
}