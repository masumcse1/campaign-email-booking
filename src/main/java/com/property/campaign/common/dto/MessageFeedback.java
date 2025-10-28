package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageFeedback {

    private Integer id;
    private Integer messageId;
    private Integer userId;
    private String comment;
    private String status;
    private String followUpDate;
    private String nextFollowUpDate;
    private String lastUpdate;
}
