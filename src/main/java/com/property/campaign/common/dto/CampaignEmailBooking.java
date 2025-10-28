package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignEmailBooking {

    private long id;
    private String name;
    private Integer emailId;
    private String emailAddress;
    private PropertyDto propertyWithAddressAndGeoCode;
    private Boolean urlForAiSystemsFound;
    private String websiteUrl;
    private String googleBusinessPlacesId;
    private String brandOfBookingEngine;
    private String googlePropertyName;

    private String screenshotOfFooterUrl;
    private String url;


}
