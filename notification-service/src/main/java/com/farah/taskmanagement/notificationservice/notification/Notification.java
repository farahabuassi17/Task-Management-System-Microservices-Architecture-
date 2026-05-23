package com.farah.taskmanagement.notificationservice.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private String taskTitle;

    @Column(nullable = false)
    private String assigneeEmail;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Instant receivedAt;

    protected Notification() {
    }

    public Notification(
            String eventId,
            Long taskId,
            String taskTitle,
            String assigneeEmail,
            String message,
            Instant receivedAt
    ) {
        this.eventId = eventId;
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.assigneeEmail = assigneeEmail;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public String getMessage() {
        return message;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}
