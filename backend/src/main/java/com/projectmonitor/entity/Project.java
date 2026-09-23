package com.projectmonitor.entity;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(length=2000) private String description;
 private LocalDate startDate; private LocalDate endDate; private Double budget;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private ProjectStatus status=ProjectStatus.NOT_STARTED;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="manager_id") private User manager;
 public Project(){} public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;} public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;} public Double getBudget(){return budget;} public void setBudget(Double v){budget=v;} public ProjectStatus getStatus(){return status;} public void setStatus(ProjectStatus v){status=v;} public User getManager(){return manager;} public void setManager(User v){manager=v;}
}
