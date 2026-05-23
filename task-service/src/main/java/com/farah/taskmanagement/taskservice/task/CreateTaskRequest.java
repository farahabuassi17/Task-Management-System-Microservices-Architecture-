package com.farah.taskmanagement.taskservice.task;

public record CreateTaskRequest(
        String title,
        String description,
        String priority,
        String boardId,
        String assignedUserId
) {
}
