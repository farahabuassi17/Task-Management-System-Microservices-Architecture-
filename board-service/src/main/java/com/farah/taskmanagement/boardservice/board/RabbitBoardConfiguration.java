package com.farah.taskmanagement.boardservice.board;

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
public class RabbitBoardConfiguration {

    @Bean
    DirectExchange boardSummaryExchange(@Value("${board.summary.exchange}") String exchangeName) {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue boardSummaryQueue(@Value("${board.summary.queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding boardSummaryBinding(
            Queue boardSummaryQueue,
            DirectExchange boardSummaryExchange,
            @Value("${board.summary.routing-key}") String routingKey
    ) {
        return BindingBuilder.bind(boardSummaryQueue).to(boardSummaryExchange).with(routingKey);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
