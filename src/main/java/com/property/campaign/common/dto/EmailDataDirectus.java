package com.property.campaign.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDataDirectus {

    @JsonProperty
    private String action;
    @JsonProperty
    private String body;
    @JsonProperty("body_placeholders")
    private String bodyPlaceholders;
    @JsonProperty("dismissTopic_link")
    private String dismissTopicLink;
    @JsonProperty
    private String domain;
    @JsonProperty
    private long id;
    @JsonProperty
    private String language;
    @JsonProperty
    private String links;
    @JsonProperty
    private String name;
    @JsonProperty
    private String permission;
    @JsonProperty("permission_action")
    private String permissionAction;
    @JsonProperty("permission_sub")
    private String permissionSub;
    @JsonProperty
    private String placeholders;
    @JsonProperty("reply_to")
    private String replyTo;
    @JsonProperty("reply_to_name")
    private String replyToName;
    @JsonProperty("sender_ews1")
    private String senderEws1;
    @JsonProperty("sender_ews2")
    private String senderEws2;
    @JsonProperty("sender_name")
    private String senderName;
    @JsonProperty("signature_email_address")
    private String signatureEmailAddress;
    @JsonProperty("signature_greetings")
    private String signatureGreetings;
    @JsonProperty("Signature_meeting")
    private String signatureMeeting;
    @JsonProperty("signature_meeting_link")
    private String signatureMeetingLink;
    @JsonProperty("signature_name")
    private String signatureName;
    @JsonProperty("signature_telephone")
    private String signatureTelephone;
    @JsonProperty("signature_title")
    private String signatureTitle;
    @JsonProperty("subject_line")
    private String subjectLine;
    @JsonProperty("target_group")
    private String targetGroup;
    @JsonProperty
    private String topic;
    @JsonProperty
    private String unsubscribe;
    @JsonProperty("unsubscribe_link")
    private String unsubscribeLink;
    @JsonProperty("sender_email")
    private String senderEmail;
    @JsonProperty("signature_photo")
    private String signaturePhoto;
    @JsonProperty("Company_Legal")
    private String companyLegal;
    @JsonProperty("Website_URL")
    private String websiteUrl;
    @JsonProperty("template")
    private String template;
    @JsonProperty("country")
    private String country;
    @JsonProperty("Campaign_Activation")
    private Boolean campaignActivation;
    @JsonProperty("Email_Limitation")
    private Integer emailLimitation;
    @JsonProperty("Weekdays")
    private List<String> weekdays;
    @JsonProperty("time")
    private String time;
    @JsonProperty("bcc_email")
    private String bccEmail;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("radius")
    private Integer radius;
    @JsonProperty("Access_to_property_in_Extranet")
    private String accessToPropertyInExtranet;
}
