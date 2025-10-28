package com.property.campaign.service;

import com.property.campaign.common.dto.*;
import com.property.campaign.common.rdo.MessageRdo;
import com.property.campaign.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.property.campaign.common.constant.MessageConstant.DATA_NOT_FOUND;


@Service
public class MessageServiceImpl implements MessageService{

    @Value("${supplier.api.url}")
    String supplierUrl;

    @Autowired
    TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public List<MessageRdo> getScheduledMessageData(String token) throws DataNotFoundException {

        int page=0;
        //fetch data from supplier
        MessageContentSupplier messageContentSupplier = getScheduledMessage(page,token);
        //get total pages in supplier API
        int totalPages = messageContentSupplier.getTotalPages();
        logger.info("Total Pages in supplier API:"+totalPages);
        //get required data afrom supplier API
        List<MessageRdo> messageRdos = messageContentSupplier.getContent();
        for (int i=1;i<totalPages;i++){
            List<MessageRdo> content = getScheduledMessage(i,token).getContent();
            messageRdos.addAll(content);
        }
        return messageRdos;
    }

    public  MessageContentSupplier getScheduledMessage(Integer i,String token) throws DataNotFoundException{

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.set("Authorization", "Bearer " + token);

        for (int j = 0; j < 3; j++) {
            //get data from supplier API
            try {
                ResponseEntity<SupplierResultDto<MessageContentSupplier>> allDataResponse = template.exchange(
                        supplierUrl + "message/v2/scheduled/paging?page="+i+"&size=100&sortBy=name&sortDesc=true",
                        HttpMethod.GET, entity, new ParameterizedTypeReference<SupplierResultDto<MessageContentSupplier>>() {
                        });
                if (allDataResponse.getStatusCodeValue() == HttpStatus.OK.value()) {
                    return Objects.requireNonNull(allDataResponse.getBody().getResult());
                }
                else
                    Thread.sleep(1000 * j);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        logger.error(DATA_NOT_FOUND);
        throw new DataNotFoundException(DATA_NOT_FOUND);
    }

    @Override
    public List<MessageRdo> getMessagesByPropertyId(Integer propertyId) throws DataNotFoundException {

        AccessTokenSupplier tokenFromSuppliers = tokenService.getTokenFromSuppliers();
        String token = tokenFromSuppliers.getAccessToken();
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String apiUrl = supplierUrl+ "/message/by-property-id/"+ propertyId;

        for (int j = 0; j < 3; j++) {
            try {
                //ResponseEntity<SupplierResultDto<ArrayList<MessageRdo>>>
                ResponseEntity<MessageContentSupplier> response = template.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<MessageContentSupplier>()  {}
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return response.getBody().getResult();
                } else {
                    logger.warn("Failed to fetch messages for property ID: " + propertyId + ". Retrying...");
                    Thread.sleep(1000 * j);
                }
            } catch (Exception e) {
                logger.error("Error fetching messages for property ID: " + propertyId, e);
            }
        }

        throw new DataNotFoundException("Unable to fetch messages for property ID: " + propertyId + " after multiple attempts.");
    }


    public List<MessageRdo> getAllScheduledMessagesPaginatedV2(Integer propertyId, String token) throws DataNotFoundException {
        int page = 0;
        int size = 100;
        List<MessageRdo> allMessages = new ArrayList<>();

        MessageContentSupplier initialContent = getScheduledMessagesPage(propertyId, page, size, token);
        int totalPages = initialContent.getTotalPages();
        logger.info("Total Pages in supplier API: " + totalPages);

        allMessages.addAll(initialContent.getContent());

        for (int i = 1; i < totalPages; i++) {
            MessageContentSupplier pageContent = getScheduledMessagesPage(propertyId, i, size, token);
            allMessages.addAll(pageContent.getContent());
        }

        if (allMessages.isEmpty()) {
            logger.error(DATA_NOT_FOUND);
            throw new DataNotFoundException(DATA_NOT_FOUND);
        }

        return allMessages;
    }

    public MessageContentSupplier getScheduledMessagesPage(Integer propertyId, int page, int size, String token) throws DataNotFoundException {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("%s/v2/by-property-id/scheduled/%d/paging?page=%d&size=%d&sortBy=name&sortDesc=true",supplierUrl, propertyId, page, size);

        // Append optional query parameters
        //if (scheduledFrom != null) url += "&scheduledFrom=" + scheduledFrom;
        //if (scheduledTo != null) url += "&scheduledTo=" + scheduledTo;

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                ResponseEntity<SupplierResultDto<MessageContentSupplier>> response = template.exchange(
                        url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return Objects.requireNonNull(response.getBody().getResult());
                } else {
                    Thread.sleep(1000 * attempt); // Progressive wait on retry
                }
            } catch (Exception e) {
                logger.error("Attempt {} - Error fetching page {}: {}", attempt + 1, page, e.getMessage());
            }
        }

        logger.error(DATA_NOT_FOUND);
        throw new DataNotFoundException(DATA_NOT_FOUND);
    }

    public List<MessageFeedback> getMessageFeedbackListByPropertyId(Integer propertyId) throws DataNotFoundException {
        int page = 0;
        int size = 100;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenService.getTokenFromSuppliers().getAccessToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String apiUrl = supplierUrl + "/message-feedback/filters?page=" + page +
                "&size=" + size + "&propertyId=" + propertyId;

        for (int j = 0; j < 3; j++) {
            try {
                ResponseEntity<MessageFeedbackContentSupplier> response = new RestTemplate().exchange(
                        apiUrl,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<MessageFeedbackContentSupplier>() {}
                );

                if (response.getStatusCode() == HttpStatus.OK &&
                        response.getBody() != null &&
                        response.getBody().getResult() != null &&
                        response.getBody().getResult().getContent() != null) {
                    return response.getBody().getResult().getContent();
                } else {
                    logger.warn("Failed to fetch message feedback for property ID: {}. Retrying...", propertyId);
                    Thread.sleep(1000 * j);
                }
            } catch (Exception e) {
                logger.error("Error fetching message feedback for property ID: " + propertyId, e);
            }
        }
        throw new DataNotFoundException("Unable to fetch message feedback for property ID: " + propertyId + " after multiple attempts.");
    }



    public MessageRdo getMessage(Integer messageId) throws DataNotFoundException {
        AccessTokenSupplier tokenFromSuppliers = tokenService.getTokenFromSuppliers();
        String token = tokenFromSuppliers.getAccessToken();

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String apiUrl = supplierUrl + "/message/" + messageId;

        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                ResponseEntity<MessageApiResponse> response = template.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<MessageApiResponse>() {
                        }
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    MessageApiResponse apiResponse = response.getBody();

                    if (apiResponse.getResult() != null) {
                        return apiResponse.getResult();
                    } else {
                        logger.warn("Received null result for message ID: " + messageId);
                    }
                } else {
                    logger.warn("Non-OK response status: " + response.getStatusCode() +" for message ID: " + messageId);
                }
            }catch (Exception e) {
                logger.error("Attempt " + (attempt + 1) + " failed for message ID: " + messageId, e);

            }
        }

        throw new DataNotFoundException("Unable to fetch message with ID: " + messageId + " after multiple attempts.");
    }



}
