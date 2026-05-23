package com.farah.taskmanagement.taskservice.task;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        String boardId,
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
                task.getPriority(),
                task.getBoardId(),
                task.getAssignedUserId(),
                task.getAssignedUserName(),
                task.getAssignedUserEmail(),
                task.getCreatedAt()
        );
    }
}
