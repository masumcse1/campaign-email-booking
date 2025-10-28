package com.property.campaign.common.restclientdto;

import lombok.Data;

@Data
public class DebounceData {
    private String email;
    private Integer code;
    private String role;
    private String free_email;
    private String result;
    private String reason;
    private String send_transactional;
    private String did_you_mean;
}
