package com.property.campaign.common.restclientdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCode {

    @JsonProperty
    @Schema(description = "Type of geolocation", example = "Point")
    private String type;

    @JsonProperty
    @Schema(description = "Latitude of the location", example = "38.8951")
    private Double latitude;

    @JsonProperty
    @Schema(description = "Longitude of the location", example = "-77.0364")
    private Double longitude;

}
