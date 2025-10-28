package com.property.campaign.service;

import com.property.campaign.common.constant.MessageConstant;
import com.property.campaign.common.dto.SupplierResultDto;
import com.property.campaign.common.rdo.EmailResultRdo;
import com.property.campaign.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Value("${supplier.api.url}")
    String supplierUrl;

    @Autowired
    CultdataService cultdataService;

    @Autowired
    TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Value("${supplier.api.url}")
    private String externalApiBaseUrl;

    private static final int MONTHS_TO_CHECK_FOR_INVOICES = 12;

    @Override
    public String saveDataToSupplierApi(String token,List<EmailResultRdo> emailForBooking) throws DataNotFoundException {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for(EmailResultRdo emailbooking :emailForBooking){
            HttpEntity<EmailResultRdo> entity = new HttpEntity<>(emailbooking,headers);
            headers.set("Authorization", "Bearer " + token);
            //store data from directus api and supplier API to message
            for (int j = 0; j < 3; j++) {
                try {
                    ResponseEntity<SupplierResultDto<EmailResultRdo>> allDataResponse = template.exchange( supplierUrl+"message",
                            HttpMethod.POST, entity, new ParameterizedTypeReference<SupplierResultDto<EmailResultRdo>>() {
                            });
                    if (allDataResponse.getStatusCodeValue() == HttpStatus.CREATED.value()){
                        logger.info("Message saved for this property ID : with message ID "+allDataResponse.getBody().getResult().getPropertyId()+
                                ","+ allDataResponse.getBody().getResult().getId());
                        break;
                    }
                    else
                        Thread.sleep(1000 * j);

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return MessageConstant.ADDITION_SUCCESS;
    }



}
