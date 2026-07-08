package org.example.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient locationRestClient() {
        return RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .build();
    }

    @Bean
    public RestClient weatherRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1")
                .build();
    }
}