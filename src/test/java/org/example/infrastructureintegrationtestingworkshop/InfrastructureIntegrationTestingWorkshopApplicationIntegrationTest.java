package org.example.infrastructureintegrationtestingworkshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.SneakyThrows;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WireMockTest(httpPort = 9090)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@ContextConfiguration(classes = {InfrastructureIntegrationTestingWorkshopApplication.class,
    InfrastructureIntegrationTestingWorkshopApplicationIntegrationTest.TestConfig.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class InfrastructureIntegrationTestingWorkshopApplicationIntegrationTest {
    public static final String URL = "/api/notifications/general";
    public static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");
    static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.8-management-alpine")
        .withVhost("RIS");

    static {
        RABBIT_MQ_CONTAINER.start();
        POSTGRESQL_CONTAINER.start();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Test
    void test() {
        stubFor(WireMock.post(urlPathEqualTo(URL))
            .willReturn(okJson("""
                {
                  "message": "goodbye"
                }
                """)
                .withHeader("Content-Type", "application/json")));
        ReminderDto request = ReminderDto.builder().message("hello").build();

        mockMvc.perform(post("/api/reminders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(request)));
        /*
        TODO: Verify rabbitmq event is created
         */

        Message received = rabbitTemplate.receive("reminder.queue", 1000);
        /*
        TODO: Verify response using objectMapper.writeValueAsString(message)
         */
        assertThat(received.getBody()).asString().isEqualTo(objectMapper.writeValueAsString(request));
        verify(postRequestedFor(urlPathEqualTo(URL)));
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    }

    @DynamicPropertySource
    static void rabbitmqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.addresses", () -> RABBIT_MQ_CONTAINER.getHost() + ":" + RABBIT_MQ_CONTAINER.getAmqpPort());
        registry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);
    }

    static class TestConfig {

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