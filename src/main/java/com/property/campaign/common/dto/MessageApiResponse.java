package com.property.campaign.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.property.campaign.common.rdo.MessageRdo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageApiResponse {
    private MessageRdo result;
    private String status;

    public MessageRdo getResult() {
        return result;
    }

    public void setResult(MessageRdo result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}