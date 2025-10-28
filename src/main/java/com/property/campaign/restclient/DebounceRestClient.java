package com.property.campaign.restclient;

import com.property.campaign.common.restclientdto.DebounceResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class DebounceRestClient {

    private final RestTemplate restTemplate;

    @Value("${debounce.api.url}")
    private String debounceApiUrl;

    @Value("${debounce.api.key}")
    private String debounceApiKey;

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public DebounceResponse verifyEmail(String email) {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(debounceApiUrl)
                .queryParam("api", debounceApiKey)
                .queryParam("email", email)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<DebounceResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                DebounceResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to verify email: " + response.getStatusCode());
        }
    }
}