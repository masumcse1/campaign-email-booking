package com.property.campaign.service;


import com.property.campaign.common.dto.AccessTokenDirectus;
import com.property.campaign.common.dto.AccessTokenSupplier;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.exception.InvalidCredentialsException;

public interface TokenService {

    public AccessTokenSupplier getTokenFromSuppliers() throws InvalidCredentialsException, DataNotFoundException;

    public AccessTokenDirectus getTokenFromDirectus() throws InvalidCredentialsException, DataNotFoundException;

}
