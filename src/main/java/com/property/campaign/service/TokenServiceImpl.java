package com.property.campaign.service;

import com.property.campaign.common.constant.MessageConstant;
import com.property.campaign.common.dto.AccessTokenDirectus;
import com.property.campaign.common.dto.AccessTokenSupplier;
import com.property.campaign.common.dto.TokenCredentials;
import com.property.campaign.common.rdo.ResultRdo;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.exception.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
public class TokenServiceImpl implements TokenService {

    @Value("${supplier.api.url}")
    String supplierUrl;

    @Value("${directus.api.url}")
    String directusUrl;

    @Value("${supplier.id.key}")
    String supplierId;

    @Value("${supplier.secret.key}")
    String supplierSecret;

    @Value("${directus.login.email}")
    String email;

    @Value("${directus.login.password}")
    String password;

    Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Override
    public AccessTokenSupplier getTokenFromSuppliers() throws InvalidCredentialsException, DataNotFoundException {

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        for(int j=0; j<3;j++) {
            try {
                ResponseEntity<AccessTokenSupplier> allDataResponse = template.exchange(
                        supplierUrl + "suppliers/get-token?supplierId=" + supplierId + "&supplierSecret=" + supplierSecret,
                        HttpMethod.POST, entity, AccessTokenSupplier.class);

              if (allDataResponse.getStatusCodeValue() == HttpStatus.OK.value()){
                    return Objects.requireNonNull(allDataResponse.getBody());
                }
                else
                    Thread.sleep(1000 * j);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        throw new InvalidCredentialsException(MessageConstant.INVALID_CREDENTIALS);
    }

    @Override
    public AccessTokenDirectus getTokenFromDirectus() throws InvalidCredentialsException, DataNotFoundException {

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TokenCredentials> entity = new HttpEntity<>(new TokenCredentials(email,password),headers);

        for(int j=0; j<3;j++) {
            try {
                ResponseEntity<ResultRdo<AccessTokenDirectus>> allDataResponse =
                        template.exchange(directusUrl + "auth/login",
                        HttpMethod.POST, entity, new ParameterizedTypeReference<ResultRdo<AccessTokenDirectus>>() {
                        });
                if (allDataResponse.getStatusCodeValue() == HttpStatus.OK.value())
                    return Objects.requireNonNull(allDataResponse.getBody().getData());
                else
                    Thread.sleep(1000 * j);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        throw new InvalidCredentialsException(MessageConstant.INVALID_CREDENTIALS);
    }


}
