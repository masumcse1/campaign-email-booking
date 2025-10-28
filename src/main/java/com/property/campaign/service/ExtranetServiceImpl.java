package com.property.campaign.service;


import com.property.campaign.common.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.property.campaign.common.constant.MessageConstant.DATA_NOT_FOUND;


@Service
public class ExtranetServiceImpl implements ExtranetService{

    Logger logger = LoggerFactory.getLogger(ExtranetServiceImpl.class);


    @Override
    public ExtranetDto setPropertyParent(Integer parent, long propertyId) {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate template = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "extranet-admin_user:cH@nGe!t";

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<ParentDto> entity = new HttpEntity<>(new ParentDto(parent),headers);

        for (int j = 0; j < 3; j++) {
            //get data from supplier API
            try {
                ResponseEntity<ExtranetDto> allDataResponse = template.exchange(
                        "http://api.extranet.roomdb.io/v2/properties/property/patch/"+propertyId,
                        HttpMethod.PATCH, entity, new ParameterizedTypeReference<ExtranetDto>() {
                        });
                if (allDataResponse.getStatusCodeValue() == HttpStatus.OK.value())
                    return Objects.requireNonNull(allDataResponse.getBody());
                else
                    Thread.sleep(1000 * j);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        logger.error(DATA_NOT_FOUND);
        return null;
    }


    @Override
    public ExtranetDto setPropertyPermissionWithUser( long propertyId,String userId) {

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = "extranet-admin_user:cH@nGe!t";

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<TokenCredentials> entity = new HttpEntity<>(headers);


        for (int j = 0; j < 3; j++) {
            //get data from supplier API
            try {
                ResponseEntity<ExtranetDto> allDataResponse = template.exchange(
                        "http://api.extranet.roomdb.io/v2/page-permissions/set-valid-admin-role/"+propertyId+"/"+userId,
                        HttpMethod.POST, entity, new ParameterizedTypeReference<ExtranetDto>() {
                        });
                if (allDataResponse.getStatusCodeValue() == HttpStatus.OK.value())
                    return Objects.requireNonNull(allDataResponse.getBody());
                else
                    Thread.sleep(1000 * j);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        logger.error(DATA_NOT_FOUND);
        return null;
    }

    @Override
    public ConsultantPropertyDto addConsultantForProperties(long propertyId, String consultantMailForSignatureEmailAddress) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Basic authentication
        String auth = "extranet-admin_user:cH@nGe!t";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        ConsultantPropertyDto requestBody = ConsultantPropertyDto.builder()
                .propertyId(propertyId)
                .userId(consultantMailForSignatureEmailAddress)
                .isVisible(true)
                .liveChat(true)
                .isActive(true)
                .advisedFrom("2025-09-18")
                .advisedUntil("2099-09-18")
                .build();

        HttpEntity<ConsultantPropertyDto> entity = new HttpEntity<>(requestBody, headers);

        for (int j = 0; j < 3; j++) {
            try {
                ResponseEntity<ConsultantPropertyDto> response = template.exchange(
                        "http://api.extranet.roomdb.io/api/v2/consultant-property",
                        HttpMethod.POST,
                        entity,
                        ConsultantPropertyDto.class
                );

                if (response.getStatusCode() == HttpStatus.OK) {
                    return response.getBody();
                } else {
                    Thread.sleep(1000 * j);
                }
            } catch (Exception e) {
                logger.error("Error adding consultant for property: " + e.getMessage());
            }
        }
        logger.error(DATA_NOT_FOUND);
        return null;
    }

    @Override
    public List<ConsultantPropertyDto> getConsultantProperties(long propertyId) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Basic authentication
        String auth = "extranet-admin_user:cH@nGe!t";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (int j = 0; j < 3; j++) {
            try {
                ResponseEntity<ConsultantPropertyResponse> response = template.exchange(
                        "http://api.extranet.roomdb.io/api/v2/consultant-property/propertyId/" + propertyId,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ConsultantPropertyResponse>() {}
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return response.getBody().getResult();
                } else {
                    Thread.sleep(1000 * j);
                }
            } catch (Exception e) {
                logger.error("Error getting consultant properties for property ID " + propertyId + ": " + e.getMessage());
            }
        }
        logger.error(DATA_NOT_FOUND);
        return Collections.emptyList();
    }



}
