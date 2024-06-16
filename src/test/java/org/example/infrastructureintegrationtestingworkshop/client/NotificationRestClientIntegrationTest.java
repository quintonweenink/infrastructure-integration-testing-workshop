package org.example.infrastructureintegrationtestingworkshop.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 9090)
@ActiveProfiles("test")
@SpringBootTest(classes = NotificationRestClientIntegrationTest.ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NotificationRestClientIntegrationTest {

    public static final String URL = "/api/notifications/general";
    @Autowired
    private NotificationRestClient sut;

    @Test
    void sendNotification() {
        stubFor(post(urlPathEqualTo(URL))
            .willReturn(okJson("""
                {
                  "message": "goodbye"
                }
                """)
                .withHeader("Content-Type", "application/json")));

        /*
        TODO: Execute key action
        TODO: Verify response
         */

        ReminderDto reminderDto = sut.sendNotification(ReminderDto.builder().message("hello").build());

        assertThat(reminderDto).isEqualTo(ReminderDto.builder().message("goodbye").build());
        verify(postRequestedFor(urlPathEqualTo(URL)));
    }

    @Test
    void getAll() {
        stubFor(get(urlPathEqualTo(URL))
            .willReturn(okJson("""
                [
                  {
                    "message": "goodbye"
                  }
                ]""")
                .withHeader("Content-Type", "application/json")));

        /*
        TODO: Execute key action
        TODO: Verify response
         */

        List<ReminderDto> results = sut.getAll();

        assertThat(results).isEqualTo(List.of(ReminderDto.builder().message("goodbye").build()));
        verify(getRequestedFor(urlPathEqualTo(URL)));
    }

    @ConfigurationPropertiesScan
    @SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
    })
    static class ServiceApplication {
        @Bean
        public RestClient restClient(RestClient.Builder restClientBuilder) {
            return restClientBuilder
                .requestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()))
                .build();
        }
    }
}