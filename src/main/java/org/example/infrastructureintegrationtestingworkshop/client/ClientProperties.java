package org.example.infrastructureintegrationtestingworkshop.client;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "service.client")
public class ClientProperties {

    @NonNull
    private Client notification;

    @Getter
    @RequiredArgsConstructor
    public static class Client {

        @NonNull
        private final String url;
    }
}
