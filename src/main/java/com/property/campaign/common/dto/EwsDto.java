package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EwsDto {

    private String subject;
    private String body;
    private String to_address;
    private String to_name;
    private String from_address;
    private String from_name;
    private String reply_address;
    private String reply_name;
    private List<String> bcc;
    private String domain;
    private String webhook_url;
    private String attachment_url;
}
