package org.example.infrastructureintegrationtestingworkshop.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    public static final String QUEUE = "notification.queue";
    public static final String ROUTING_KEY = "notification.created";
    public static final String EXCHANGE = "amq.topic";

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.nonDurable(QUEUE).build();
    }

    @Bean
    Binding binding(Exchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(ROUTING_KEY)
            .noargs();
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setObservationEnabled(true);
        return rabbitTemplate;
    }
}
