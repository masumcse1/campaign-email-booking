package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

     private Integer phoneTypeId;
     private String phoneNumber;
     private String extension;
     private boolean isValidated;
     private PhoneType phoneType;
}
