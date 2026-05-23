package com.farah.taskmanagement.notificationservice.notification;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTaskEventConfiguration {

    @Bean
    DirectExchange taskEventsExchange(@Value("${task.events.exchange}") String exchangeName) {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue taskEventsQueue(@Value("${task.events.queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding taskEventsBinding(
            Queue taskEventsQueue,
            DirectExchange taskEventsExchange,
            @Value("${task.events.routing-key}") String routingKey
    ) {
        return BindingBuilder.bind(taskEventsQueue).to(taskEventsExchange).with(routingKey);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
