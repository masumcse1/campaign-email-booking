package com.property.campaign.service;

import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.exception.InvalidCredentialsException;

import java.util.List;

public interface DirectusDataService {

    public List<EmailDataDirectus> getDataFromDirectusApi(String token) throws DataNotFoundException, InvalidCredentialsException;

}
