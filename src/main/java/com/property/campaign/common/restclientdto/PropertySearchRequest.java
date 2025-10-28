package com.property.campaign.common.restclientdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertySearchRequest {

    private String latitude;

    private String longitude;

    private String radius;

    private String pageNumber;

    private String pageSize;

    private List<String> distributorType;

}
