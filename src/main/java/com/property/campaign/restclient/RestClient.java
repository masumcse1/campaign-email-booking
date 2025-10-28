package com.property.campaign.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.campaign.common.restclientdto.CustomPageImpl;
import com.property.campaign.common.restclientdto.PropertySearchRequest;
import com.property.campaign.common.restclientdto.PropertySearchResult;
import com.property.campaign.util.AppUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Data
@Component
public class RestClient {


    private static final Logger infoLogger = LoggerFactory.getLogger("INFO_LOGGER");

    private boolean distributorMongoAvailable;

    private final RestTemplate restTemplateDistributorMongo;


    public boolean getHealthCheckDistributorMongo(String url) {
        infoLogger.info("HealthCheck Distributor-Mongo-Tool.");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            HttpEntity<String> request = new HttpEntity<>(headers);

            long requestStartTime = System.nanoTime();

            ResponseEntity<String> response = restTemplateDistributorMongo.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            int status = response.getStatusCode().value();
            String body = response.getBody();

            long requestTime = (System.nanoTime() - requestStartTime) / 1000000; // result in milliseconds

            infoLogger.info("HealthCheck Distributor-Mongo-Tool. Response Status: {}, Execution time: {}", status, requestTime);

            // Went successful or not
            distributorMongoAvailable = status == HttpStatus.OK.value() && body != null;

            return distributorMongoAvailable;
        } catch (Exception e) {
            infoLogger.info("Failed to sent HealthCheck Distributor-Mongo-Tool.");
            infoLogger.info(e.getMessage(), e);
        }
        return false;
    }


    public CustomPageImpl<PropertySearchResult> searchProperties(
            @NotNull String url, String username, String password,
            @NotNull Double latitude, @NotNull Double longitude, Integer radius, Integer page, Integer pageSize, List<String> distributorType
    ) {
        infoLogger.info("Distributor-Mongo-Tool search properties.");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("username", username);
        headers.set("password", password);

        if (radius == null) {
            radius = AppUtils.DEFAULT_SEARCH_RADIUS;
        }

        if(distributorType == null || distributorType.isEmpty() )
        {
            distributorType = AppUtils.DEFAULT_DISTRIBUTORTYPE;
        }

        PropertySearchRequest searchRequest = new PropertySearchRequest(latitude.toString(), longitude.toString(), radius.toString(), page.toString(), pageSize.toString(), distributorType);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(searchRequest);
        } catch (JsonProcessingException e) {
            infoLogger.info(e.getMessage(), e);
        }

        CustomPageImpl<PropertySearchResult> result = null;

        try {
            HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

            long requestStartTime = System.nanoTime();
            ResponseEntity<CustomPageImpl<PropertySearchResult>> response = restTemplateDistributorMongo.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<CustomPageImpl<PropertySearchResult>>() {
                    }
            );

            long requestTime = (System.nanoTime() - requestStartTime) / 1000000; // result in milliseconds

            int status = response.getStatusCode().value();
            if (status == HttpStatus.OK.value()) {
                result = response.getBody();

                int size = 0;
                if (result != null) {
                    size = result.getNumberOfElements();
                }
                infoLogger.info("Distributor-Mongo-Tool Request: {}, Response Status: {}, Response count: {}, Request time: {}",
                        request, status, size, requestTime);
            } else {
                infoLogger.info("Distributor-Mongo-Tool Error Request: {}, Response Status: {}, Request time in milliseconds: {}",
                        request, status, requestTime);
            }
        } catch (Exception e) {
            infoLogger.info("Failed to search with Distributor-Mongo-Tool.");
            infoLogger.info(e.getMessage(), e);
        }
        return result;
    }
}
