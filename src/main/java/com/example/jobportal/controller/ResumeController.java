package com.example.jobportal.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobportal.model.Resume;
import com.example.jobportal.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('CANDIDATE')")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads/resumes/";

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            File saveFile = new File(uploadDir + fileName);
            file.transferTo(saveFile);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("path", "uploads/resumes/" + fileName);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 IMPORTANT: now you will see REAL error
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
   
 // ADMIN + RECRUITER + OWNER CANDIDATE
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public ResponseEntity<Resume> getResume(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resumeService.getResumeById(id));
    }

    // ADMIN + RECRUITER
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER')")
    public ResponseEntity<List<Resume>> getAllResumes() {

        return ResponseEntity.ok(
                resumeService.getAllResumes());
    }

    // ADMIN + RECRUITER + CANDIDATE
    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public ResponseEntity<List<Resume>> getUserResumes(
            @PathVariable String email) {

        return ResponseEntity.ok(
                resumeService.getResumesByEmail(email));
    }

    // DOWNLOAD
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','RECRUITER','CANDIDATE')")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long id)
            throws IOException {

        File file = resumeService.downloadResume(id);

        Resource resource =
                new UrlResource(file.toPath().toUri());

        String contentType =
                Files.probeContentType(file.toPath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + file.getName()
                                + "\"")
                .body(resource);
    }

    // ADMIN ONLY
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteResume(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                resumeService.deleteResume(id));
    }    }