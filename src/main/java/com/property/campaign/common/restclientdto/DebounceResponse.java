package com.property.campaign.common.restclientdto;

import lombok.Data;

@Data
public class DebounceResponse {
    private DebounceData debounce;
    private String success;
    private String balance;
}