package com.property.campaign.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.property.campaign.common.rdo.MessageRdo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContentSupplier {

    private List<MessageRdo> content;

    private List<MessageRdo> result;

    private int totalPages;

    private int totalElements;

    private boolean last;

    private int number;

    private int size;

    private int numberOfElements;

    private boolean first;

    private boolean empty;
}
