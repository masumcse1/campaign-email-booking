package com.property.campaign.common.restclientdto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertySearchResponse {

    @JsonProperty
    @Schema(description = "RoomDB property id.", example = "1000010")
    private Integer id;

    @JsonProperty
    @Schema(description = "Property name", example = "Leisure hotels")
    private String name;

    @JsonProperty
    @Schema(description = "DistributorType", example = "cultSwitch/eHotel/google")
    private String distributorType;

    @JsonProperty
    @Schema(description = "Property geolocation")
    private GeoCode geoCode;

    private long campaignId;
}
