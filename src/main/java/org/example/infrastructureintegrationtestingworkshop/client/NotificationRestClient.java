package org.example.infrastructureintegrationtestingworkshop.client;

import lombok.RequiredArgsConstructor;
import org.example.infrastructureintegrationtestingworkshop.core.model.ReminderDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NotificationRestClient {

    private static final String NOTIFICATION_URI = "/general";

    private final RestClient restClient;
    private final ClientProperties clientProperties;

    public ReminderDto sendNotification(ReminderDto notificationEnvelope) {
        String uri = clientProperties.getNotification().getUrl() + NOTIFICATION_URI;

        return restClient.post()
            .uri(uri)
            .body(notificationEnvelope)
            .retrieve()
            .body(ReminderDto.class);
    }

    public List<ReminderDto> getAll() {
        String uri = clientProperties.getNotification().getUrl() + NOTIFICATION_URI;

        return Arrays.stream(Objects.requireNonNull(restClient.get()
            .uri(uri)
            .retrieve()
            .body(ReminderDto[].class))).toList();
    }

}