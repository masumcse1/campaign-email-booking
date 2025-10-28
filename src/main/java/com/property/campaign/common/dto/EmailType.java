package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailType {
    private Integer id;
    private String code;
    private String name;
}
