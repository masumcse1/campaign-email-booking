package com.property.campaign.common.dto;

import com.property.campaign.common.rdo.MessageRdo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private Integer id;
    private String name;
    private String cultSwitchId;
    private String code;
    private String alternativeName;
    private String status;
    private boolean emailAddressUnsubscribe;
    private String emailAddressComment;
    private String forTesting;
    private boolean isMaster;
    private boolean isDeletedBySupplier;
    private List<EmailEntity> emails;
    private List<MessageRdo> messages;
    private HomeCurrency homeCurrency;
    private List<PhoneDto> phones;
    private List<AddressDto> addresses;
    private String website;
    private Integer emailId;
    private String email;
}
