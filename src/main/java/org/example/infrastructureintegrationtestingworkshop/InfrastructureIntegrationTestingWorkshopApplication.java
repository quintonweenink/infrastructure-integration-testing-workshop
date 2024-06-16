package org.example.infrastructureintegrationtestingworkshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;


@SpringBootApplication
@ConfigurationPropertiesScan
public class InfrastructureIntegrationTestingWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfrastructureIntegrationTestingWorkshopApplication.class, args);
    }

    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
            .requestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()))
            .build();
    }
}
