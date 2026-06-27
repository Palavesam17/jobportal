package com.example.jobportal.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String company;

    private String location;

    @Column(length = 5000)
    private String description;

    private Double salary;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @OneToMany(mappedBy = "job",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
 private List<Application> applications;
    
 

	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
   

  

	public Job() {
    }

    public Job(String title,
            String company,
            String location,
            String description,
            Double salary,User createdBy) {
     this.title = title;
     this.company = company;
     this.location = location;
     this.description = description;
     this.salary = salary;
     this.createdBy=createdBy;
     
     }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
    public List<Application> getApplications() {
 		return applications;
 	}

 	public void setApplications(List<Application> applications) {
 		this.applications = applications;
 	}

 	public User getUser() {
 		return user;
 	}

 	public void setUser(User user) {
 		this.user = user;
 	}
    
    
}