package com.property.campaign.restclient;

import com.property.campaign.common.response.ApiResponse;
import com.property.campaign.common.restclientdto.EmailDto;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.service.TokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Data
@RequiredArgsConstructor
@Component
public class EmailRestClient {

    private final RestTemplate restTemplate;

    @Value("${supplier.api.url}")
    private String emailApiBaseUrl;

    @Autowired
    private TokenService tokenService;

    public EmailDto setEmailStatus(Integer emailId, String status) throws DataNotFoundException {
        String cleanedBaseUrl = emailApiBaseUrl.replaceAll("/$", "");
        String apiUrl = String.format("%s/properties/set-email-status/%d/%s", cleanedBaseUrl, emailId, status);
        String token = tokenService.getTokenFromSuppliers().getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse<EmailDto>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.PATCH,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        ApiResponse<EmailDto> apiResponse = response.getBody();
        if (apiResponse != null && "success".equals(apiResponse.getStatus())) {
            return apiResponse.getResult();
        } else {
            throw new RuntimeException("Failed to update email status: " +
                    (apiResponse != null ? apiResponse.getMessage() : "Unknown error"));
        }
    }
}
