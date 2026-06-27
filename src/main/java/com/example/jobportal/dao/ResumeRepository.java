package com.example.jobportal.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobportal.model.Resume;

public interface ResumeRepository
        extends JpaRepository<Resume, Long> {

    List<Resume> findByUserId(Long userId);
    
    Optional<Resume> findByFileName(String fileName);

    void deleteByFileName(String fileName);
}