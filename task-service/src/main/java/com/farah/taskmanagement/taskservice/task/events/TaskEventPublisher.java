package com.farah.taskmanagement.taskservice.task.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(TaskEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public TaskEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${task.events.exchange}") String exchangeName,
            @Value("${task.events.routing-key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void publish(TaskUpdatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
        } catch (AmqpException ex) {
            log.warn("Task event was not published because RabbitMQ is unavailable: {}", ex.getMessage());
        }
    }
}
