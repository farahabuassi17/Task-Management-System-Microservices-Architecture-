package com.farah.taskmanagement.taskservice.task.graphql;

public record CreateTaskInput(
        String title,
        String description,
        String priority,
        String boardId,
        String assignedUserId
) {
}
