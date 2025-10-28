package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

    private Integer id;
    private String name;
    private String fullName;
    private String code;
    private String codeA3;
}