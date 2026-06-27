package com.example.jobportal.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobportal.dao.ResumeRepository;
import com.example.jobportal.model.Resume;
import com.example.jobportal.model.User;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final AuthService authService;

    public ResumeService(
            ResumeRepository resumeRepository,
            AuthService authService) {

        this.resumeRepository = resumeRepository;
        this.authService = authService;
    }

    public String uploadResume(
            MultipartFile file,
            String email) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select a file");
        }

        User user = authService.findByEmail(email);

        String uploadDir =
                System.getProperty("user.dir")
                        + File.separator + "uploads";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName =
                UUID.randomUUID() + "_"
                        + file.getOriginalFilename();

        String filePath =
                uploadDir + File.separator
                        + uniqueFileName;

        file.transferTo(new File(filePath));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(uniqueFileName);
        resume.setFilePath(filePath);

        resumeRepository.save(resume);

        return "Resume uploaded successfully";
    }

    public Resume getResumeById(Long id) {

        return resumeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public List<Resume> getResumesByEmail(
            String email) {

        User user =
                authService.findByEmail(email);

        return resumeRepository
                .findByUserId(user.getId());
    }

    public File downloadResume(Long id) {

        Resume resume = getResumeById(id);

        File file =
                new File(resume.getFilePath());

        if (!file.exists()) {
            throw new RuntimeException(
                    "Resume file not found");
        }

        return file;
    }

    public String deleteResume(Long id) {

        Resume resume =
                getResumeById(id);

        File file =
                new File(resume.getFilePath());

        if (file.exists()) {
            file.delete();
        }

        resumeRepository.delete(resume);

        return "Resume deleted successfully";
    }
}