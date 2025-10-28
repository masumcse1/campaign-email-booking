package com.property.campaign.service;


import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.restclientdto.CustomPageImpl;
import com.property.campaign.common.restclientdto.PropertySearchResponse;
import com.property.campaign.common.restclientdto.PropertySearchResult;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.restclient.RestClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PropertiesSearchServiceImpl implements PropertiesSearchService {

    private final RestClient restClient;

    @Value("${distributor-mongo.access-url}")
    String accessUrlDistributorMongo;

    @Value("${distributor-mongo.search-endpoint}")
    String searchEndpointDistributorMongo;

    @Value("${distributor-mongo.username}")
    String usernameDistributorMongo;

    @Value("${distributor-mongo.password}")
    String passwordDistributorMongo;

    Logger logger = LoggerFactory.getLogger(PropertiesSearchServiceImpl.class);
    // analysis
    @Override
    public List<PropertySearchResponse> getPropertiesByGeocodeAndRadius(List<EmailDataDirectus> emailDataDirectusList) throws DataNotFoundException {
        Integer pages = 0;
        Integer size = 1000;
        List<String> distributorType = Arrays.asList("A", "B", "C");

        List<List<PropertySearchResult>> propertySearchResults = new ArrayList<>();

        List<PropertySearchResponse> propertiesListWithGeoCode = null;

        for (EmailDataDirectus emailDataDirectus : emailDataDirectusList) {
            if (Objects.nonNull(emailDataDirectus.getCountry()) && Objects.nonNull(emailDataDirectus.getCampaignActivation()) && emailDataDirectus.getCampaignActivation()) {

                CustomPageImpl<PropertySearchResult> propertySearchResultCustomPage = searchProperties(emailDataDirectus.getLatitude(), emailDataDirectus.getLongitude(),
                        emailDataDirectus.getRadius(), pages, size, distributorType);
                if (propertySearchResultCustomPage == null) {
                    continue;
                }
                int totalPagesGeo = propertySearchResultCustomPage.getTotalPages();
                propertySearchResults.add(propertySearchResultCustomPage.getContent());

                for (int i = 1; i < totalPagesGeo; i++) {
                    propertySearchResultCustomPage = searchProperties(emailDataDirectus.getLatitude(),  emailDataDirectus.getLongitude(), emailDataDirectus.getRadius(), i, size, distributorType);
                    if (propertySearchResultCustomPage == null) {
                        continue;
                    }
                    List<PropertySearchResult> content = propertySearchResultCustomPage.getContent();

                    content.forEach(m -> {
                        if (m.getContent() != null) {
                            m.getContent().setCampaignId(emailDataDirectus.getId());
                        }
                    });

                    propertySearchResults.add(propertySearchResultCustomPage.getContent());
                }
            }
        }
        propertiesListWithGeoCode = propertySearchResults.stream().flatMap(List::stream).map(PropertySearchResult::getContent).collect(Collectors.toList());
        return propertiesListWithGeoCode;
    }

    public CustomPageImpl<PropertySearchResult> searchProperties(Double latitude, Double longitude, Integer radius, Integer page, Integer size, List<String> distributorType) {
        boolean isDistributorMongoAvailable = isDistributorMongoAvailable();
        if (!isDistributorMongoAvailable) {
            return null;
        }

        CustomPageImpl<PropertySearchResult> result = null;
        // Retry logic: Try up to 3 times with increasing wait intervals
        for (int j = 0; j < 3; j++) {
            try {
                result = restClient.searchProperties(
                        accessUrlDistributorMongo + searchEndpointDistributorMongo,
                        usernameDistributorMongo, passwordDistributorMongo,
                        latitude, longitude, radius, page, size, distributorType
                );

                if (result != null) {
                    break;
                } else {
                    Thread.sleep(1000 * (j + 1));
                }
            } catch (Exception e) {
                logger.error("Attempt " + (j + 1) + " failed: " + e.getMessage());
                try {
                    Thread.sleep(1000 * (j + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted during retry wait: " + ie.getMessage());
                    break;
                }
            }
        }

        return result;
    }



    public boolean isDistributorMongoAvailable() {

        return restClient.isDistributorMongoAvailable();

    }
}
