package com.farah.taskmanagement.taskservice.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String assignedUserId;

    @Column(nullable = false)
    private String assignedUserName;

    @Column(nullable = false)
    private String assignedUserEmail;

    @Column(nullable = false)
    private Instant createdAt;

    protected Task() {
    }

    public Task(
            String title,
            String description,
            String status,
            String assignedUserId,
            String assignedUserName,
            String assignedUserEmail,
            Instant createdAt
    ) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUserId = assignedUserId;
        this.assignedUserName = assignedUserName;
        this.assignedUserEmail = assignedUserEmail;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public String getAssignedUserName() {
        return assignedUserName;
    }

    public String getAssignedUserEmail() {
        return assignedUserEmail;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
