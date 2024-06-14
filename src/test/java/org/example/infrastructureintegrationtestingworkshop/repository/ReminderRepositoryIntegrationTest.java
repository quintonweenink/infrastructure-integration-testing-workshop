package org.example.infrastructureintegrationtestingworkshop.repository;

import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

//@Testcontainers
@Transactional
@SpringBootTest(classes = ReminderRepositoryIntegrationTest.DummyApplication.class)
@ActiveProfiles("test")
class ReminderRepositoryIntegrationTest {

//    @Container
//    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private ReminderJpaRepository reminderJpaRepository;

//    @AfterEach
//    void tearDown() {
//        reminderJpaRepository.deleteAll();
//    }

    @Test
    void create() {
        /*
        TODO: Execute key action
        TODO: Verify result by checking how many instance are in the database
         */
    }

//    @DynamicPropertySource
//    static void postgresqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
//        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
//    }

    @ConfigurationPropertiesScan
    @SpringBootApplication
    static class DummyApplication {

    }
}