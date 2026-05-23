package com.farah.taskmanagement.taskservice.task.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BoardSummaryRequestPublisher {

    private static final Logger log = LoggerFactory.getLogger(BoardSummaryRequestPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public BoardSummaryRequestPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${board.summary.exchange}") String exchangeName,
            @Value("${board.summary.routing-key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void publish(BoardSummaryRequestedEvent event) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
        } catch (AmqpException ex) {
            log.warn("Board summary request was not published because RabbitMQ is unavailable: {}", ex.getMessage());
        }
    }
}
