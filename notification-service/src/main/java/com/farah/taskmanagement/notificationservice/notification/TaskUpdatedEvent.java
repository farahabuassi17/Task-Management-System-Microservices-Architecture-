package com.farah.taskmanagement.notificationservice.notification;

import java.time.Instant;

public record TaskUpdatedEvent(
        String eventId,
        String eventType,
        Instant timestamp,
        Payload payload
) {
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
