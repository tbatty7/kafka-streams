package com.battybuilds.kafkastreams;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class IntegrationTest {
    @Test
    public void hitControllerToSendMessage() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/send";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }
}
