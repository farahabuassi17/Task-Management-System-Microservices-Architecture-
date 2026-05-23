package com.farah.taskmanagement.notificationservice.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TaskEventListener {

    private final NotificationRepository notificationRepository;

    public TaskEventListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = "${task.events.queue}")
    public void handleTaskUpdated(TaskUpdatedEvent event) {
        TaskUpdatedEvent.Payload payload = event.payload();
        notificationRepository.save(new Notification(
                event.eventId(),
                payload.taskId(),
                payload.taskTitle(),
                payload.assigneeEmail(),
                payload.alertMessage(),
                Instant.now()
        ));
    }
}
