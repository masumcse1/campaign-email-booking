package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenSupplier {

    private String tokenType;
    private String accessToken;
    private int executionTime;
    private int expiresIn;
}
