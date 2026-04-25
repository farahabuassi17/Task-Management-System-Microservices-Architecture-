package com.farah.taskmanagement.taskservice.task.graphql;

public record CreateTaskInput(
        String title,
        String description,
        String assignedUserId
) {
}
