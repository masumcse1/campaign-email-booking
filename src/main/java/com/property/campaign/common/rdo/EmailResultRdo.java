package com.property.campaign.common.rdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailResultRdo {

    private long id;
    private long propertyId;
    private String name;
    private String topic;
    private String subject;
    private String toAddress;
    private String toName;
    private String fromAddress;
    private String fromName;
    private String replyAddress;
    private String replyName;
    private String domain;
    private String provider;
    private String body;
    private String scheduledFor;
    private boolean permission;
    private boolean dismissTopic;
    private boolean unsubscribe;
    private String details;
    private String ewsId;
    private String dismissTopicIPv4;
    private String permissionIPv4;
    private String unsubscribeIPv4;
    private Integer campaignId;
}
