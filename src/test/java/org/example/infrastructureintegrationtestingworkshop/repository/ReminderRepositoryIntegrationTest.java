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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@Transactional
@SpringBootTest(classes = ReminderRepositoryIntegrationTest.RepositoryApplication.class)
@ActiveProfiles("test")
class ReminderRepositoryIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14");

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private ReminderJpaRepository reminderJpaRepository;

    @AfterEach
    void tearDown() {
        reminderJpaRepository.deleteAll();
    }

    @Test
    void create() {
        /*
        TODO: Execute key action
        TODO: Verify result by checking how many instance are in the database
         */
        reminderRepository.create(ReminderDto.builder().message("hello").build());

        assertThat(reminderRepository.getAll()).hasSize(1);

    }

    @Test
    void findById() {
        /*
        TODO: Execute key action
        TODO: Verify result by checking how many instance are in the database
         */
        ReminderDto reminder = ReminderDto.builder().message("hello").build();
        reminderRepository.create(reminder);
        Optional<ReminderDto> result = reminderRepository.findByMessage("hello");
        Optional<ReminderDto> resultNotFound = reminderRepository.findByMessage("other");

        assertThat(result).hasValue(reminder);
        assertThat(resultNotFound).isEmpty();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    }

    @ConfigurationPropertiesScan
    @SpringBootApplication
    static class RepositoryApplication {

    }
}