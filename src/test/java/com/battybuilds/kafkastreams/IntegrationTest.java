package com.battybuilds.kafkastreams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@IfProfileValue(name="integration.test", value = "true")
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
public class IntegrationTest {

    @Value("${server.port}")
    private String port;

    @Test
    public void hitControllerToSendMessage() {
        System.out.println(port);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/send";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        response.getStatusCode();
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
}
