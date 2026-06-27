package com.example.jobportal.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String refreshToken;
    
  

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private CandidateProfile candidateProfile;

    @OneToMany(mappedBy = "createdBy")
    private List<Job> jobs;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Application> applications;

    public User() {
    }
    
    public User(String name,
            String email,
            String password,
            Role role,String refreshToken) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.refreshToken=refreshToken;
    
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public CandidateProfile getCandidateProfile() {
  		return candidateProfile;
  	}

  	public void setCandidateProfile(CandidateProfile candidateProfile) {
  		this.candidateProfile = candidateProfile;
  	}

  	public List<Job> getJobs() {
  		return jobs;
  	}

  	public void setJobs(List<Job> jobs) {
  		this.jobs = jobs;
  	}

  	public List<Application> getApplications() {
  		return applications;
  	}

  	public void setApplications(List<Application> applications) {
  		this.applications = applications;
  	}
  	
  	public String getRefreshToken() {
  	    return refreshToken;
  	}

  	public void setRefreshToken(String refreshToken) {
  	    this.refreshToken = refreshToken;
  	}
}