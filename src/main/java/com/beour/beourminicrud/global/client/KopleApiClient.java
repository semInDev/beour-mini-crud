package com.beour.beourminicrud.global.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KopleApiClient {

    private static final String KOPLE_BASE_URL = "https://supabase.kople.io/functions/v1";

    public String getEvents(String clerkToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + clerkToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(KOPLE_BASE_URL + "/events", HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
