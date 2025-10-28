package com.property.campaign.util;


import com.property.campaign.common.dto.CampaignEmailBooking;
import com.property.campaign.common.dto.EmailDataDirectus;

import java.util.Objects;
import java.util.regex.Matcher;

import static com.property.campaign.common.constant.EmailTemplateConstant.*;


public class EmailBodyPlaceholder {

    public static String unsubscribeTextReplace(String unsubscribe){

        String replaceUnsubscribeText1 =unsubscribe.replaceFirst("%<a2>", """
                <a href="%dismissTopic%" target="_blank" style="color: #618BEC;">
                """);

        String replaceUnsubscribeText2 =replaceUnsubscribeText1.replaceFirst("</a2>%", "</a>");

        String replaceUnsubscribeText3 =replaceUnsubscribeText2.replaceFirst("%<a3>", """
                <a href="%unsubscribe%" target="_blank" style="color: #618BEC;">
                """);

      return  replaceUnsubscribeText3.replaceFirst("</a3>%", "</a>");
    }

    public static String[] linkReplaceAll(String links){

        String replaceAllTag = links.replaceAll("<[^>]*>", "");
        return replaceAllTag.split("%");
    }

    public static  String combineEmailDataFromSupplier(CampaignEmailBooking campaignEmailBooking, EmailDataDirectus emailDataDirectus, String param, String template){

        String calendlyBody="";

        String numberBody="";

        if (template.equalsIgnoreCase("CultSwitch")) {

            template = CULTSWITCH_EMAIL_TEMPLATE;
            calendlyBody=CULTSWITCH_CALENDLY;
            numberBody=CULTSWITCH_NUMBER;
        }
        else if(template.equalsIgnoreCase("MappingMaster")) {

            template = EMAIL_TEMPLATE_PT;
            calendlyBody=MM_CALENDLY;
            numberBody=MM_NUMBER;

        }

        String replaceBodyPlaceHolder =template.replaceFirst("%body_placeholder%",emailDataDirectus.getBodyPlaceholders());

        //replace name
        String replaceName =replaceBodyPlaceHolder.replaceAll("%property_name%",Objects.nonNull(campaignEmailBooking.getGooglePropertyName())?
                campaignEmailBooking.getGooglePropertyName(): campaignEmailBooking.getName());

        String replaceId =replaceName.replaceAll("%property_id%", String.valueOf(campaignEmailBooking.getId()));
        //replace map url
        String replaceMap =replaceId.replaceFirst("%property_screenshot_of_footer_url%",
                Matcher.quoteReplacement("<img src=\""+ campaignEmailBooking.getScreenshotOfFooterUrl()+"\" width=\"520\" style=\"max-width: 520px; vertical-align: middle;\" class=\"fluid\" alt=\"\">\n"
                      ));
        //replace signature name
        String replaceSignatureName =replaceMap.replaceFirst("%signature_name%",
                emailDataDirectus.getSignatureName());
        //replace signature title
        String replaceSignatureTitle =replaceSignatureName.replaceFirst("%signature_title%",
                emailDataDirectus.getSignatureTitle());

        String signatureTelephone=Objects.nonNull(emailDataDirectus.getSignatureTelephone())?
                emailDataDirectus.getSignatureTelephone():"";

        String numberBodyData=Objects.nonNull(emailDataDirectus.getSignatureTelephone())? numberBody:"";

        String replaceNumberBody =replaceSignatureTitle.replaceFirst("%numberbody%",numberBodyData);

        //replace signature number
        String replaceSignatureNumber =replaceNumberBody.replaceAll("%signature_telephone%",signatureTelephone);
        //replace signature email
        String replaceSignatureEmail =replaceSignatureNumber.replaceAll("%signature_email_address%",
                emailDataDirectus.getSignatureEmailAddress());
        //replace permission text
        String replaceP6 =replaceSignatureEmail.replaceFirst("%p6%", emailDataDirectus.getPermission().split("(<a1>|</a1>)")[1]);
        //replace signature permission
        String replaceP7 =replaceP6.replaceFirst("%p7%", emailDataDirectus.getPermissionSub());
        //replace signature action
        String replaceP8 =replaceP7.replaceFirst("%p8%", emailDataDirectus.getAction());
        //replace signature greetings
        String replaceP9 =replaceP8.replaceFirst("%p9%", emailDataDirectus.getSignatureGreetings());
        //dynamic data in unsubscribe text
        String replaceUnsubscribeText =replaceP9.replaceFirst(" %unsubscribe_text%", unsubscribeTextReplace(emailDataDirectus.getUnsubscribe()));
        //split all links
        String [] allLinks=linkReplaceAll(emailDataDirectus.getLinks());
        //replace permission url
        String replacePermissionURL =replaceUnsubscribeText.replaceAll("%permission%", allLinks[1]+param);

        //replace permission url
        String replaceUnsubscribeURL =replacePermissionURL.replaceFirst("%unsubscribe%", allLinks[5]+param);

        //replace permission url
        String replaceDismissURL =replaceUnsubscribeURL.replaceFirst("%dismissTopic%", allLinks[3]+param);

        String signatureMeetingLink=Objects.nonNull(emailDataDirectus.getSignatureMeetingLink())?
                emailDataDirectus.getSignatureMeetingLink():"";

        String calendlyBodyData=Objects.nonNull(emailDataDirectus.getSignatureMeetingLink())? calendlyBody:"";

         String replaceCalenderBody =replaceDismissURL.replaceFirst(" %calendlybody%",calendlyBodyData);

        //replace calendly link
        String replaceCalenderURL =replaceCalenderBody.replaceFirst("%calendly%",signatureMeetingLink.replaceAll("<a4>|</a4>",""));

        String replaceEmail =replaceCalenderURL.replaceAll("%property_email%", campaignEmailBooking.getEmailAddress().trim());

        String replaceSignaturePhoto =replaceEmail.replaceFirst("%signature_photo%",emailDataDirectus.getSignaturePhoto());

        String websiteURL="<br>"+"<a style=\"text-decoration: none; white-space: nowrap; color: black;\" href=\""+emailDataDirectus.getWebsiteUrl()+"\">"+emailDataDirectus.getWebsiteUrl()+"</a>";

        String replaceCompanyLegal =replaceSignaturePhoto.replaceFirst("%companyLegal%",emailDataDirectus.getCompanyLegal().replaceAll("\n","<br>")+websiteURL);

        String replaceSignatureMeeting =replaceCompanyLegal.replaceFirst("%Signature_meeting%",  Objects.nonNull(emailDataDirectus.getSignatureMeeting()) ? emailDataDirectus.getSignatureMeeting().replaceAll("%<a4>|</a4>%","") : "");

        String propertyName= Objects.nonNull(campaignEmailBooking.getGooglePropertyName())? campaignEmailBooking.getGooglePropertyName(): campaignEmailBooking.getName();

        String removeAltTag = replaceSignatureMeeting.replace("alt=\"" + propertyName + " Google Metasearch\"", " ");

        return removeAltTag.replaceFirst("%property_screenshot_of_footer_url%",
                "<img src=\""+ campaignEmailBooking.getScreenshotOfFooterUrl()+"\" width=\"520\" style=\"max-width: 520px; vertical-align: middle;\" class=\"fluid\" alt=\"\">");
    }



}

