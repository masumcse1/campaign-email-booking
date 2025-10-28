package com.property.campaign.service;

import com.property.campaign.common.dto.CampaignEmailBooking;
import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.restclientdto.PropertySearchResponse;
import com.property.campaign.exception.DataNotFoundException;

import java.util.List;

public interface PropertyService {

    public List<CampaignEmailBooking> getSupplierDataByGeocode(List<EmailDataDirectus> directusCampaignListWithGeoCode,String token, int days, List<PropertySearchResponse> geocodeWisePropertyList) throws DataNotFoundException ;

    public List<CampaignEmailBooking> getSupplierData(String token, int days, List<EmailDataDirectus> emailDataDirectusList) throws DataNotFoundException;


}
