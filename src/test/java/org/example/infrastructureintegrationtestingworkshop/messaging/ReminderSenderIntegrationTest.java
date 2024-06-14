package org.example.infrastructureintegrationtestingworkshop.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ReminderSenderIntegrationTest.MessagingApplication.class)
@ActiveProfiles("test")
class ReminderSenderIntegrationTest {

//    static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.8-management-alpine").withVhost("RIS");
//    static {
//        RABBIT_MQ_CONTAINER.start();
//    }

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReminderSender sut;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @SneakyThrows
    @Test
    void sendEvent() {

        /*
        TODO: Execute key action
         */

        Message received = rabbitTemplate.receive(MessagingApplication.QUEUE, 1000);
        /*
        TODO: Verify response using objectMapper.writeValueAsString(message)
         */
    }

//    @DynamicPropertySource
//    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.rabbitmq.addresses", () -> RABBIT_MQ_CONTAINER.getHost() + ":" + RABBIT_MQ_CONTAINER.getAmqpPort());
//        registry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
//        registry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);
//    }

    @SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        SecurityAutoConfiguration.class,
    })
    static class MessagingApplication {

        public static final String QUEUE = "reminder.queue";
        public static final String ROUTING_KEY = "reminder.created";
        public static final String EXCHANGE = "amq.topic";

//        @Bean
//        Exchange exchange() {
//            return ExchangeBuilder.topicExchange(EXCHANGE).build();
//        }
//
//        @Bean
//        Queue queue() {
//            return QueueBuilder.nonDurable(QUEUE).build();
//        }
//
//        @Bean
//        Binding binding(Exchange exchange, Queue queue) {
//            return BindingBuilder.bind(queue)
//                .to(exchange)
//                .with(ROUTING_KEY)
//                .noargs();
//        }
//
//        @Bean
//        public MessageConverter messageConverter(ObjectMapper objectMapper) {
//            return new Jackson2JsonMessageConverter(objectMapper);
//        }
    }
}