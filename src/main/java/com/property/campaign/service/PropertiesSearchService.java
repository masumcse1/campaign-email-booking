package com.property.campaign.service;


import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.restclientdto.PropertySearchResponse;

import java.util.List;

public interface PropertiesSearchService {

     List<PropertySearchResponse> getPropertiesByGeocodeAndRadius(List<EmailDataDirectus> emailDataDirectusList);

}
