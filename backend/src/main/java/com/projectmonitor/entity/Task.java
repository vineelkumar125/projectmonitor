package com.projectmonitor.entity;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="tasks")
public class Task {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title; @Column(length=2000) private String description; private LocalDate deadline;
 @Enumerated(EnumType.STRING) private Priority priority=Priority.MEDIUM; @Enumerated(EnumType.STRING) private TaskStatus status=TaskStatus.PENDING;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="project_id",nullable=false) private Project project;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assigned_to") private User assignedTo;
 public Task(){} public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public LocalDate getDeadline(){return deadline;} public void setDeadline(LocalDate v){deadline=v;} public Priority getPriority(){return priority;} public void setPriority(Priority v){priority=v;} public TaskStatus getStatus(){return status;} public void setStatus(TaskStatus v){status=v;} public Project getProject(){return project;} public void setProject(Project v){project=v;} public User getAssignedTo(){return assignedTo;} public void setAssignedTo(User v){assignedTo=v;}
}
