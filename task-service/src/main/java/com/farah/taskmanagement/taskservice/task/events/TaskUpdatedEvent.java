package com.farah.taskmanagement.taskservice.task.events;

import java.time.Instant;
import java.util.UUID;

public record TaskUpdatedEvent(
        String eventId,
        String eventType,
        Instant timestamp,
        Payload payload
) {
    public static TaskUpdatedEvent statusChanged(
            Long taskId,
            String taskTitle,
            String oldStatus,
            String newStatus,
            String assigneeEmail
    ) {
        return new TaskUpdatedEvent(
                UUID.randomUUID().toString(),
                "TaskStatusChanged",
                Instant.now(),
                new Payload(
                        taskId,
                        taskTitle,
                        oldStatus,
                        newStatus,
                        assigneeEmail,
                        "Task status has been updated to " + newStatus + "."
                )
        );
    }

    public record Payload(
            Long taskId,
            String taskTitle,
            String oldStatus,
            String newStatus,
            String assigneeEmail,
            String alertMessage
    ) {
    }
}
