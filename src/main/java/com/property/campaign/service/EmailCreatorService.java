package com.property.campaign.service;

import com.property.campaign.common.dto.CampaignEmailBooking;
import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.rdo.EmailResultRdo;
import com.property.campaign.exception.DataNotFoundException;

import java.util.List;

public interface EmailCreatorService {

     List<EmailResultRdo> getEmailForBooking(List<EmailDataDirectus> emailDataDirectuses,
                                             List<CampaignEmailBooking> campaignEmailBookings,
                                             String token) throws DataNotFoundException;


     boolean isValidCampaignEmail(CampaignEmailBooking campaignEmailBooking, Integer emailId, String email)
             throws DataNotFoundException;
}
