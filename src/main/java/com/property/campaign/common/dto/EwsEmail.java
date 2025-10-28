package com.property.campaign.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EwsEmail {

     private String _id;
     private String body;
     private String created_at;
     private String status;
     private List<String> bcc;
     private String webhook_url;
}
