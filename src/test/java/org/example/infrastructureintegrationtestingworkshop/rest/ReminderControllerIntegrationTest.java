package org.example.infrastructureintegrationtestingworkshop.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.infrastructureintegrationtestingworkshop.core.ReminderService;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ReminderControllerIntegrationTest.RestApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReminderControllerIntegrationTest {

    @MockBean
    protected ReminderService reminderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void createReminder() {
        ReminderDto response = ReminderDto.builder().message("goodbye").build();
        ReminderDto request = ReminderDto.builder().message("hello").build();
        when(reminderService.createReminder(eq(request))).thenReturn(response);

        mockMvc.perform(post("/api/reminders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(reminderService).createReminder(request);
    }

    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
    static class RestApplication {

    }
}