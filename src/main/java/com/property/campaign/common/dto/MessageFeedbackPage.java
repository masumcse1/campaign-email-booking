package com.property.campaign.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageFeedbackPage {
    private List<MessageFeedback> content;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private int number;
    private int size;
    private int numberOfElements;
    private boolean first;
    private boolean empty;
}