package com.property.campaign.service;

import com.property.campaign.common.rdo.EmailResultRdo;
import com.property.campaign.exception.DataNotFoundException;

import java.util.List;

public interface SupplierService {

    public String saveDataToSupplierApi(String token,List<EmailResultRdo> emailForBooking) throws DataNotFoundException;


}
