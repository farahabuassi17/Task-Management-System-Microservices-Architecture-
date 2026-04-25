package com.farah.taskmanagement.taskservice.task;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String assignedUserId,
        String assignedUserName,
        String assignedUserEmail,
        Instant createdAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignedUserId(),
                task.getAssignedUserName(),
                task.getAssignedUserEmail(),
                task.getCreatedAt()
        );
    }
}
