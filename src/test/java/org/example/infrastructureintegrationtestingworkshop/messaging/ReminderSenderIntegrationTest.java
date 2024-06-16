package org.example.infrastructureintegrationtestingworkshop.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.infrastructureintegrationtestingworkshop.core.ReminderService;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ReminderSenderIntegrationTest.MessagingApplication.class)
@ActiveProfiles("test")
class ReminderSenderIntegrationTest {

    static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.8-management-alpine")
        .withVhost("RIS");
    static {
        RABBIT_MQ_CONTAINER.start();
    }

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReminderSender sut;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @MockBean
    private ReminderService reminderService;


    @SneakyThrows
    @Test
    void sendEvent() {

        /*
        TODO: Execute key action
         */
        ReminderDto message = ReminderDto.builder().message("hello").build();
        sut.sendEvent(ReminderDto.builder().message("hello").build());

        Message received = rabbitTemplate.receive(MessagingApplication.QUEUE, 1000);
        /*
        TODO: Verify response using objectMapper.writeValueAsString(message)
         */
        assertThat(received.getBody()).asString().isEqualTo(objectMapper.writeValueAsString(message));
    }

    @SneakyThrows
    @Test
    void listenForEvent() {
        /*
        TODO: Execute key action
         */

        ReminderDto message = ReminderDto.builder().message("hello").build();
        when(reminderService.createReminder(message)).thenReturn(message);

        rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTING_KEY, message);
        /*
        TODO: Verify response using objectMapper.writeValueAsString(message)
         */
        verify(reminderService, timeout(1_000)).createReminder(message);
    }

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.addresses", () -> RABBIT_MQ_CONTAINER.getHost() + ":" + RABBIT_MQ_CONTAINER.getAmqpPort());
        registry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);
    }

    @ConfigurationPropertiesScan
    @SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
    })
    static class MessagingApplication {

        public static final String QUEUE = "reminder.queue";
        public static final String ROUTING_KEY = "reminder.created";

        @Bean
        Queue mockQueue() {
            return QueueBuilder.nonDurable(QUEUE).build();
        }

        @Bean
        Binding mockBinding(Exchange exchange, Queue mockQueue) {
            return BindingBuilder.bind(mockQueue)
                .to(exchange)
                .with(ROUTING_KEY)
                .noargs();
        }
    }
}