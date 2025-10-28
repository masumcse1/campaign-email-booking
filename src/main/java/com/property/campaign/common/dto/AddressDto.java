package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Integer id;
    private String addressType;
    private String addressLine;
    private String cityName;
    private CountryDto country;
}