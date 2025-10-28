package com.property.campaign.service;


import com.property.campaign.common.dto.ConsultantPropertyDto;
import com.property.campaign.common.dto.ExtranetDto;

import java.util.List;

public interface ExtranetService {

    ExtranetDto setPropertyParent(Integer parent, long propertyId);

    ExtranetDto setPropertyPermissionWithUser( long propertyId,String userId);

    public ConsultantPropertyDto addConsultantForProperties(long propertyId, String userId);

    public List<ConsultantPropertyDto> getConsultantProperties(long propertyId);

}
