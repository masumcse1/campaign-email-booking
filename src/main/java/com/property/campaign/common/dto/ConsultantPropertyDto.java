package com.property.campaign.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultantPropertyDto {
    private long propertyId;
    private String userId;

    @JsonProperty("isVisible")
    private boolean isVisible;

    private boolean liveChat;

    @JsonProperty("isActive")
    private boolean isActive;

    private String advisedFrom;
    private String advisedUntil;
}
