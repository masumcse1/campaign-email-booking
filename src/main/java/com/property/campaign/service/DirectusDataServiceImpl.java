package com.property.campaign.service;

import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.dto.TokenCredentials;
import com.property.campaign.common.rdo.ResultRdo;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.exception.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.property.campaign.common.constant.MessageConstant.DATA_NOT_FOUND;


@Service
public class DirectusDataServiceImpl implements   DirectusDataService{

    @Value("${directus.api.url}")
    String directusUrl;

    @Autowired
     TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(DirectusDataServiceImpl.class);
    @Override
    public List<EmailDataDirectus> getDataFromDirectusApi(String token) throws DataNotFoundException, InvalidCredentialsException {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TokenCredentials> entity = new HttpEntity<>(headers);

        for(int j=0; j<3;j++){

            headers.set("Authorization", "Bearer " + token);
            try {
                ResponseEntity<ResultRdo<List<EmailDataDirectus>>> allDataResponse =template.exchange(directusUrl+"items/campaigns",
                        HttpMethod.GET, entity,  new ParameterizedTypeReference<ResultRdo<List<EmailDataDirectus>>>() {});

                if(allDataResponse.getStatusCodeValue() == HttpStatus.OK.value()){
                    return Objects.requireNonNull(allDataResponse.getBody().getData());
                }
                else
                    Thread.sleep(1000*j);

            }catch (Exception e){
                token=tokenService.getTokenFromDirectus().getAccessToken();
                logger.error(e.getMessage());
            }
        }

        throw new DataNotFoundException(DATA_NOT_FOUND);
    }
}
