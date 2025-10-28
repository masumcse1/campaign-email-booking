package com.property.campaign.service;


import com.property.campaign.common.dto.CultDataDto;
import com.property.campaign.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.property.campaign.common.constant.MessageConstant.DATA_NOT_FOUND;


@Service
public class CultdataServiceImpl implements CultdataService {

    private static final Logger logger = LoggerFactory.getLogger(CultdataServiceImpl.class);

    @Value("${cultdata.api.key}")
    private String apiKey;

    @Value("${cultdata.api.base-url}")
    private String baseUrl;

    @Override
    public Boolean hadInvoiceInMonths(String cultSwitchID, int invoiceCheckMonths) throws DataNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = baseUrl + "api/clients/" + cultSwitchID + "/had-invoices";

        String fromDate = LocalDate.now()
                .minusMonths(invoiceCheckMonths)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("fromDate", fromDate)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                logger.info("Attempt {} - Checking invoices for Client ID: {}, fromDate: {}", attempt + 1, cultSwitchID, fromDate);

                ResponseEntity<CultDataDto> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<CultDataDto>() {}
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return Objects.requireNonNull(response.getBody()).isHad_invoices();
                } else {
                    logger.warn("Attempt {}: Non-OK status code {}", attempt + 1, response.getStatusCodeValue());
                    Thread.sleep(1000L * (attempt + 1));
                }

            } catch (Exception e) {
                logger.error("Attempt {} failed: {}", attempt + 1, e.getMessage());
            }
        }

        logger.error(DATA_NOT_FOUND);
        return null;
    }
}
