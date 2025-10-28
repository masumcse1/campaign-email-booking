package com.property.campaign.service;

import com.property.campaign.common.dto.*;
import com.property.campaign.common.rdo.EmailResultRdo;
import com.property.campaign.common.rdo.MessageRdo;
import com.property.campaign.common.restclientdto.DebounceResponse;
import com.property.campaign.common.restclientdto.EmailDto;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.restclient.DebounceRestClient;
import com.property.campaign.restclient.EmailRestClient;
import com.property.campaign.util.DomainMappingProperties;
import com.property.campaign.util.EmailBodyPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.property.campaign.util.DateTimeUtil.compareDates;
import static com.property.campaign.util.DateTimeUtil.convertTimeStampToString;
import static com.property.campaign.util.ValidUrlCheck.isValidUrl;


@Service
public class EmailCreatorServiceImpl implements  EmailCreatorService{

    Logger logger = LoggerFactory.getLogger(EmailCreatorServiceImpl.class);

    @Autowired
    private DebounceRestClient debounceRestClient;

    @Autowired
    private EmailRestClient emailRestClient;

    @Autowired
    ExtranetService extranetService;

    @Autowired
    MessageService messageService;

    @Autowired
    private DomainMappingProperties domainMapping;

    @Value("${campaign.schedule.day.interval}")
    private int campaignScheduleDayInterval;

    @Value("${campaign.resend.delay.days}")
    private int campaignResendDelayDays;

    private static final int MONTHS_TO_CHECK_FOR_INVOICES = 12;

    @Override
    public List<EmailResultRdo> getEmailForBooking(List<EmailDataDirectus> emailDataDirectuses,
                                                   List<CampaignEmailBooking> campaignEmailBookings, String token) throws DataNotFoundException {


        logger.info("Supplier size "+ campaignEmailBookings.size());

        List<EmailResultRdo> emailResultRdos = new ArrayList<>();

        Set<Long> propertyAdded = new HashSet<>();

        List<MessageRdo> messageRdos = messageService.getScheduledMessageData(token);

        for (EmailDataDirectus emailDataDirectus : emailDataDirectuses) {

                int limit = checkScheduledDate(token,emailDataDirectus,messageRdos,campaignScheduleDayInterval);

                if(limit>0) {
                    List<String> countryList = Arrays.stream(emailDataDirectus.getCountry().split(",")).map(String::trim).toList();

                    List<EmailResultRdo> emailCampaignList = new ArrayList<>();

                    for (CampaignEmailBooking campaignEmailBooking : campaignEmailBookings) {

                        for (AddressDto addressDto : campaignEmailBooking.getPropertyWithAddressAndGeoCode().getAddresses()) {

                            if (countryList.contains(addressDto.getCountry().getCode()) && !propertyAdded.contains(campaignEmailBooking.getId())) {

                                if (skipInvalidCampaignEmailBooking(emailDataDirectus,campaignEmailBooking)) {       // TODO -4
                                    continue;
                                }

                                boolean isExistsCampaignEmailByCampaignID=false;
                                List<MessageRdo> messageListWithPropertyID = messageService.getMessagesByPropertyId(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getId());

                                if (messageListWithPropertyID == null || messageListWithPropertyID.isEmpty()) {
                                    isExistsCampaignEmailByCampaignID = false;
                                } else {
                                    isExistsCampaignEmailByCampaignID = messageListWithPropertyID.stream()
                                            .anyMatch(message ->  message.getCampaignId() != null &&   message.getCampaignId().longValue() == emailDataDirectus.getId());
                                }

                                boolean isExistsMessageFeedbackWithSameTopic=false;
                                isExistsMessageFeedbackWithSameTopic = isExistsMessageFeedbackWithSameTopic(emailDataDirectuses, emailDataDirectus, campaignEmailBooking.getPropertyWithAddressAndGeoCode().getId());


                                // If campaign message already exists and was sent within 90 days, skip
                                boolean isRecentCampaignMessage = hasRecentCampaignMessage(emailDataDirectus,campaignEmailBooking);

                                boolean shouldSendCampaignEmail = !isRecentCampaignMessage && !isExistsMessageFeedbackWithSameTopic && !isExistsCampaignEmailByCampaignID && isValidCampaignEmail(campaignEmailBooking, campaignEmailBooking.getEmailId(), campaignEmailBooking.getEmailAddress());

                                if(shouldSendCampaignEmail) {

                                    logger.info("Perfect property={} campaign={}: ", campaignEmailBooking.getId(), emailDataDirectus.getId());
                                    //parameter for the link
                                    String parameter = "?propertyId=" + campaignEmailBooking.getId() + "&topic=" + emailDataDirectus.getTopic() +
                                            "&name=" + emailDataDirectus.getName() + "&toAddress=" + campaignEmailBooking.getEmailAddress() + "&campaignId=" + emailDataDirectus.getId();

                                    //get email body
                                    String emailBody = getEmailBody(emailDataDirectus, campaignEmailBooking, parameter); // TODO -5
                                    String ewsProvider = domainMapping.getEwsProvider(domainMapping.extractMailServer(emailDataDirectus.getDomain()));

                                    EmailResultRdo emailResult = getEmailResultRdo(emailDataDirectus, campaignEmailBooking, ewsProvider, emailBody);
                                    emailCampaignList.add(emailResult);
                                    emailResultRdos.add(emailResult);
                                    propertyAdded.add(campaignEmailBooking.getId());

                                    extranetService.setPropertyPermissionWithUser(campaignEmailBooking.getId(),emailDataDirectus.getSignatureEmailAddress());

                                    String accessEmails = emailDataDirectus.getAccessToPropertyInExtranet();
                                    if (accessEmails != null && !accessEmails.isBlank()){
                                        List<String> userEmailList = Arrays.stream(emailDataDirectus.getAccessToPropertyInExtranet().split(",")).map(String::trim).toList();
                                        for(String userEmail: userEmailList){
                                            extranetService.setPropertyPermissionWithUser(campaignEmailBooking.getId(),userEmail);
                                        }
                                    }
                                    Integer parent = emailDataDirectus.getTemplate().equalsIgnoreCase("CultSwitch") ? 1870096 : 1870097;

                                    extranetService.setPropertyParent(parent, campaignEmailBooking.getId());

                                    List<ConsultantPropertyDto> consultantProperty = extranetService.getConsultantProperties(campaignEmailBooking.getId());
                                    boolean consultantPropertyExists = consultantProperty.stream().findAny().isPresent();
                                    if (!consultantPropertyExists) {
                                        extranetService.addConsultantForProperties(campaignEmailBooking.getId(), emailDataDirectus.getSignatureEmailAddress());
                                        logger.info("Added Consultant property for property Id: " + campaignEmailBooking.getId());
                                    } else {
                                        logger.info("Consultant property already exists for property Id: " + campaignEmailBooking.getId());
                                    }

                                    logger.info("Access added to the email={} for the property={}", emailDataDirectus.getSignatureEmailAddress(), campaignEmailBooking.getId());
                                    break;
                                }
                            }
                        }

                        if (emailCampaignList.size() >= limit) {
							logger.info("check campaign email limit--Campaign ID ={} campaign limit ={} email  size={}", emailDataDirectus.getId(), emailDataDirectus.getEmailLimitation(),emailCampaignList.size());
                            break;
                        }
                    }
                }

        }

        logger.info("Number of emails to be stored in message API :"+emailResultRdos.size());
        return emailResultRdos;
    }

    private static String getEmailBody(EmailDataDirectus emailDataDirectus, CampaignEmailBooking campaignEmailBooking, String parameter) {
        return EmailBodyPlaceholder.combineEmailDataFromSupplier(campaignEmailBooking, emailDataDirectus, parameter, emailDataDirectus.getTemplate());
    }

    private boolean skipInvalidCampaignEmailBooking(EmailDataDirectus emailDataDirectus,CampaignEmailBooking campaignEmailBooking) {
        return !isValidUrl(campaignEmailBooking.getScreenshotOfFooterUrl());
    }

    private EmailResultRdo getEmailResultRdo(EmailDataDirectus emailDataDirectus, CampaignEmailBooking campaignEmailBooking, String ewsProvider, String emailBody) {
        EmailResultRdo emailResult = new EmailResultRdo();
        emailResult.setPropertyId(campaignEmailBooking.getId());
        emailResult.setName(emailDataDirectus.getName());
        emailResult.setTopic(emailDataDirectus.getTopic());
        emailResult.setSubject(emailDataDirectus.getSubjectLine());
        emailResult.setToAddress(campaignEmailBooking.getEmailAddress());
        emailResult.setToName(campaignEmailBooking.getName());
        emailResult.setFromAddress(emailDataDirectus.getSenderEmail());
        emailResult.setFromName(emailDataDirectus.getSenderName());
        emailResult.setReplyAddress(emailDataDirectus.getReplyTo());
        emailResult.setReplyName(emailDataDirectus.getReplyToName());
        emailResult.setDomain(emailDataDirectus.getDomain());
        emailResult.setProvider(ewsProvider);
        emailResult.setBody(emailBody);
        emailResult.setScheduledFor(convertTimeStampToString(emailDataDirectus, campaignScheduleDayInterval));
        emailResult.setCampaignId((int) emailDataDirectus.getId());
        return emailResult;
    }

    private boolean hasRecentCampaignMessage(EmailDataDirectus emailDataDirectus,CampaignEmailBooking campaignEmailBooking) {
        boolean isResendCampaignFlag = false;
        String CAMPAIGN_TOPIC = emailDataDirectus.getTopic();

        List<MessageRdo> messageList = messageService.getMessagesByPropertyId(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getId());
        Optional<MessageRdo> lastMessage = messageList.stream()
                .filter(m -> CAMPAIGN_TOPIC.equals(m.getTopic()))
                .max(Comparator.comparing(MessageRdo::getScheduledFor));

        if (lastMessage.isPresent()) {
            OffsetDateTime scheduledDateTime = OffsetDateTime.parse(lastMessage.get().getScheduledFor());
            LocalDate scheduledDate = scheduledDateTime.toLocalDate();
            long daysDifference = ChronoUnit.DAYS.between(scheduledDate, LocalDate.now());

            isResendCampaignFlag = daysDifference < campaignResendDelayDays;
            logger.info(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getId()+"Resend campaign: " + daysDifference + " days have passed - " + isResendCampaignFlag);
        }

        return isResendCampaignFlag;
    }


    private boolean isExistsMessageFeedbackWithSameTopic(List<EmailDataDirectus> emailDataDirectusList, EmailDataDirectus emailDataDirectus,Integer propertyID) {
        boolean isExitsSameTopic = false;

        List<MessageFeedback> messageFeedbackList = messageService.getMessageFeedbackListByPropertyId(propertyID);

        if (messageFeedbackList.isEmpty()) {
            logger.info("No message feedback  for property {}", propertyID);
            return false;
        }


        List<Integer> messageIds = messageFeedbackList.stream().map(m->m.getMessageId()).distinct().toList();

        logger.info("Found message feedback  for property {}", propertyID);

        List<MessageRdo>  messageRdoList = new ArrayList<>();
        for(Integer messageID: messageIds){
            MessageRdo messageRdo = messageService.getMessage(messageID);
            messageRdoList.add(messageRdo);
        }

        logger.info("Found message Rdo feedback  for property {}", propertyID);

        List<Long> distinctCampaignIds = messageRdoList.stream()
                .map(MessageRdo::getCampaignId)
                .filter(Objects::nonNull)
                .map(Integer::longValue)
                .distinct()
                .toList();

        List<String> directusCampaignTopicList = emailDataDirectusList.stream()
                .filter(m -> distinctCampaignIds.contains(m.getId()))
                .map(EmailDataDirectus::getTopic)
                .collect(Collectors.toList());

        if(directusCampaignTopicList.contains(emailDataDirectus.getTopic())){
            isExitsSameTopic= true;
            logger.info(" message feedback found for property with same topic {}", propertyID);
        }
        return isExitsSameTopic;

    }


    private int checkScheduledDate(String token,EmailDataDirectus emailDataDirectus,List<MessageRdo> messageRdos,int campaignScheduleDayInterval) throws DataNotFoundException {

        String scheduledDate = convertTimeStampToString(emailDataDirectus,campaignScheduleDayInterval);

        if (scheduledDate == null) {
            return 0; // If it's not a weekday, limit is 0
        }

        long matchingMessagesCount = messageRdos.stream()
                .filter(messageRdo -> messageRdo.getCampaignId() != null &&  (int)emailDataDirectus.getId()== messageRdo.getCampaignId() &&   compareDates(scheduledDate, messageRdo.getScheduledFor())).count();
        //remaining campaign limit
        return emailDataDirectus.getEmailLimitation() - (int) matchingMessagesCount;

    }


    public boolean isValidCampaignEmail(CampaignEmailBooking campaignEmailBooking, Integer emailId, String email)
            throws DataNotFoundException {

        final String EMAIL_STATUS_BOUNCED = "BOUNCED";
        final int INVALID_BOUNCE_CODE = 6;
        final String INVALID_BOUNCE_TEXT_CODE = "Invalid";

        try {
            DebounceResponse response = debounceRestClient.verifyEmail(email);
            logger.info("Email Verification Response: {}", response);

            boolean isEmailInvalid = response.getDebounce().getCode().equals(INVALID_BOUNCE_CODE) ||
                    response.getDebounce().getResult().equalsIgnoreCase(INVALID_BOUNCE_TEXT_CODE);

            if (isEmailInvalid) {
                EmailDto emailDto = emailRestClient.setEmailStatus(emailId, EMAIL_STATUS_BOUNCED);
                logger.info("BOUNCED email for Property ID={} | Email ID={} | Email={} | Response={}", campaignEmailBooking.getId(), emailId, email, emailDto);
                return false;
            }
            return true;

        } catch (Exception e) {
            logger.warn("Email verification failed after retries for Property ID={} | Email={}: {}", campaignEmailBooking.getId(), email, e.getMessage());
            return true;
        }
    }

}
