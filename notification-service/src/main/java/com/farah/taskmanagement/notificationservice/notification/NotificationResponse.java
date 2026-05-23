package com.farah.taskmanagement.notificationservice.notification;

import java.time.Instant;

public record NotificationResponse(
        Long id,
        String eventId,
        Long taskId,
        String taskTitle,
        String assigneeEmail,
        String message,
        Instant receivedAt
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getEventId(),
                notification.getTaskId(),
                notification.getTaskTitle(),
                notification.getAssigneeEmail(),
                notification.getMessage(),
                notification.getReceivedAt()
        );
    }
}
