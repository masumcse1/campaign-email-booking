package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeCurrency {

    private Integer id;
    private String code;
    private String name;
    private Integer numericCode;
    private String symbol;
    private Integer minorUnit;
}
