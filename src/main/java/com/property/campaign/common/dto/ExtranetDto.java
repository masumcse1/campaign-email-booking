package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtranetDto {

    private Integer propertyId;

    private Integer roleId;

    private Integer adminRoleId;

    private String email;
}
