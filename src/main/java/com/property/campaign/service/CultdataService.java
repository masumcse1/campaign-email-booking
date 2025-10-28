package com.property.campaign.service;


import com.property.campaign.exception.DataNotFoundException;

public interface CultdataService {

    public Boolean hadInvoiceInMonths(String cultSwitchID, int invoiceCheckMonths) throws DataNotFoundException;
}
