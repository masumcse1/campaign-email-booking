package com.property.campaign.service;

import com.property.campaign.common.dto.*;
import com.property.campaign.common.rdo.MessageRdo;
import com.property.campaign.common.restclientdto.PropertySearchResponse;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.model.Country;
import com.property.campaign.model.Property;
import com.property.campaign.repository.PropertyRepository;
import com.property.campaign.repository.PropertySpecification;
import com.property.campaign.util.DateTimeUtil;
import com.property.campaign.util.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private CultdataService cultdataService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private MessageService messageService;

    private static final int MONTHS_TO_CHECK_FOR_INVOICES = 12;

    Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    @Override
    public List<CampaignEmailBooking> getSupplierDataByGeocode(List<EmailDataDirectus> directusCampaignListWithGeoCode,String token, int days, List<PropertySearchResponse> geocodeWisePropertyList) throws DataNotFoundException {

        Date lastUpdateAfterDate = DateTimeUtil.getDateOfDaysBeforeNowIrrespectiveOfTime(days);

        List<CampaignEmailBooking> campaignEmailBookings = new ArrayList<>();
        List<MessageRdo> messageRdos = messageService.getScheduledMessageData(token);

        for (PropertySearchResponse propertySearchResponse : geocodeWisePropertyList) {

            CampaignEmailBooking campaignEmailBooking = getDataByPropertyId(propertySearchResponse.getId(), lastUpdateAfterDate, messageRdos);

            if (Objects.nonNull(campaignEmailBooking)) {
                List<CampaignEmailBooking> campaignEmailBookingArrayList = new ArrayList<>();
                campaignEmailBookingArrayList.add(campaignEmailBooking);

                List<EmailDataDirectus> targetEmailCampaignDirectus = directusCampaignListWithGeoCode.stream().filter(m -> m.getId() == propertySearchResponse.getCampaignId()).toList();

                List<CampaignEmailBooking> campaignEmailBookingList = filterSupplierData(targetEmailCampaignDirectus.getFirst(),campaignEmailBookingArrayList);

                if (!campaignEmailBookingList.isEmpty()) {
                    campaignEmailBookings.addAll(campaignEmailBookingList);
                }
            }

        }
        return campaignEmailBookings;
    }


    @Override
    public List<CampaignEmailBooking> getSupplierData(String token, int days, List<EmailDataDirectus> emailDataDirectusList) throws DataNotFoundException {

        Date lastUpdateAfterDate = DateTimeUtil.getDateOfDaysBeforeNowIrrespectiveOfTime(days);

        List<CampaignEmailBooking> campaignEmailBookings = new ArrayList<>();
        List<MessageRdo> messageRdos = messageService.getScheduledMessageData(token);

        for (EmailDataDirectus emailDataDirectus : emailDataDirectusList) {

                List<String> countryList = Arrays.stream(emailDataDirectus.getCountry().split(",")).map(String::trim).toList();

                List<Country> campaignCountryList = countryService.findByCodeIn(countryList);

                List<Integer> countryIdList = campaignCountryList.stream().map(Country::getId).collect(Collectors.toList());

                List<Property> properties = findProperties(countryIdList,lastUpdateAfterDate);  // TODO -1

                if (Objects.isNull(properties) || properties.isEmpty()) {
                    continue;
                }
                List<CampaignEmailBooking> campaignEmailBookingList = convertPropertiesToCampaignEmailBookingList(properties,  messageRdos);   // TODO -2
                campaignEmailBookings.addAll(filterSupplierData(emailDataDirectus,campaignEmailBookingList));

        }
        return campaignEmailBookings;
    }


    private List<CampaignEmailBooking> filterSupplierData(EmailDataDirectus emailDataDirectus,List<CampaignEmailBooking> campaignEmailBookings) {

        List<CampaignEmailBooking> campaignEmailBookingList = new ArrayList<>();

        for (CampaignEmailBooking campaignEmailBooking : campaignEmailBookings) {

            //find primary email
            String email = campaignEmailBooking.getPropertyWithAddressAndGeoCode().getEmail();


            if (!CollectionUtils.isEmpty(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getAddresses())
                    && Objects.nonNull(email)
                    && !campaignEmailBooking.getPropertyWithAddressAndGeoCode().isEmailAddressUnsubscribe()) {

                Boolean hadInvoiceExistsInMonths  = false;

                if (Objects.nonNull(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getCultSwitchId()) &&
                        !campaignEmailBooking.getPropertyWithAddressAndGeoCode().getCultSwitchId().isEmpty()) {


                    hadInvoiceExistsInMonths  = cultdataService.hadInvoiceInMonths (campaignEmailBooking.getPropertyWithAddressAndGeoCode().getCultSwitchId(),MONTHS_TO_CHECK_FOR_INVOICES );

                    logger.info("Invoice={} for property ID : {}", hadInvoiceExistsInMonths, campaignEmailBooking.getId());

                }

                if (hadInvoiceExistsInMonths ) continue;
                Boolean flag = false;

                for (MessageRdo messageRdo : campaignEmailBooking.getPropertyWithAddressAndGeoCode().getMessages()) {

                    if ((messageRdo.getTopic().equalsIgnoreCase(emailDataDirectus.getTopic()) && messageRdo.isDismissTopic()) ||   messageRdo.getName().equalsIgnoreCase(emailDataDirectus.getName())) {
                        flag = true;
                        break;
                    }
                }
                //if message already exist
                if (flag ) continue;

                campaignEmailBooking.setEmailId(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getEmailId());
                campaignEmailBooking.setEmailAddress(email);
                campaignEmailBooking.setName(campaignEmailBooking.getPropertyWithAddressAndGeoCode().getName());

                logger.info("Property Id Added to the email box : {}", campaignEmailBooking.getId());
                campaignEmailBookingList.add(campaignEmailBooking);
            }
        }

        logger.info("Supplier size :" + campaignEmailBookingList.size());

        List<CampaignEmailBooking> campaignEmailBookingInfoList = campaignEmailBookingList.stream().filter                              //TODO - 3
                (campaignEmailBooking -> Objects.nonNull(campaignEmailBooking.getScreenshotOfFooterUrl())
                                              && Objects.nonNull(campaignEmailBooking.getGoogleBusinessPlacesId())
                                              && EmailValidator.isValid(campaignEmailBooking.getEmailAddress())).toList();

        logger.info("Number of emails to be stored in message API :" + campaignEmailBookingInfoList.size());

        return campaignEmailBookingList;
    }


    public CampaignEmailBooking getDataByPropertyId(Integer propertyID,  Date lastUpdateAfterDate, List<MessageRdo> messageRdos) throws DataNotFoundException {

        Optional<Property> propertyOptional = findProperty(propertyID, lastUpdateAfterDate);
        Property property = propertyOptional.orElse(null);

        if (property == null) return null;
        Country country = countryService.getCountryById(property.getCountry().getId()).get();

        List<MessageRdo> filteredMessageRdosByPropertyID = messageRdos.stream()
                .filter(message -> propertyID.equals(message.getPropertyId()))
                .collect(Collectors.toList());


        CampaignEmailBooking campaignEmailBooking = convertPropertyToCampaignEmailBooking(property, filteredMessageRdosByPropertyID);
        return campaignEmailBooking;
    }

    public CampaignEmailBooking convertPropertyToCampaignEmailBooking(Property property, List<MessageRdo> messageRdos) throws DataNotFoundException {

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(property.getId());
        propertyDto.setName(property.getName());
        propertyDto.setEmailAddressUnsubscribe(property.getEmailAddressUnsubscribe());
        propertyDto.setEmailId(property.getEmailId());
        propertyDto.setEmail(property.getEmail());
        propertyDto.setCultSwitchId(property.getCultSwitchId());
        if (Objects.nonNull(messageRdos) || !messageRdos.isEmpty()) {
            propertyDto.setMessages(messageRdos);
        }

        AddressDto addressDto = new AddressDto();
        CountryDto countryDto = new CountryDto();
        countryDto.setId(property.getCountry().getId());
        countryDto.setCode(property.getCountry().getCode());

        addressDto.setCountry(countryDto);
        List<AddressDto> addressDtos = new ArrayList<>();
        addressDtos.add(addressDto);
        propertyDto.setAddresses(addressDtos);

        CampaignEmailBooking campaignEmailBooking = new CampaignEmailBooking();
        campaignEmailBooking.setId(property.getId());
        campaignEmailBooking.setGooglePropertyName(property.getGooglePropertyName());
        campaignEmailBooking.setUrlForAiSystemsFound(property.getUrlForAiSystemsFound());
        campaignEmailBooking.setGoogleBusinessPlacesId(property.getGoogleBusinessID());
        campaignEmailBooking.setScreenshotOfFooterUrl(property.getScreenshotOfFooterUrl());

        campaignEmailBooking.setPropertyWithAddressAndGeoCode(propertyDto);
        return campaignEmailBooking;
    }

    public List<CampaignEmailBooking> convertPropertiesToCampaignEmailBookingList(List<Property> properties, List<MessageRdo> messageRdos) {
        List<CampaignEmailBooking> campaignEmailBookingList = new ArrayList<>();

        for (Property property : properties) {

            if (property != null) {
                List<MessageRdo> messageRdoListByPropertyID = messageRdos.stream()
                        .filter(message -> property.getPropertyId().equals(message.getPropertyId()))
                        .collect(Collectors.toList());
                CampaignEmailBooking campaignEmailBooking = convertPropertyToCampaignEmailBooking(property,  messageRdoListByPropertyID);
                campaignEmailBookingList.add(campaignEmailBooking);
            }
        }
        return campaignEmailBookingList;
    }


    public Optional<Property> findProperty(Integer propertyId,  Date urlForAiSystemsFoundLastUpdate) {
        Boolean urlForAiSystemsFound = false;
        Specification<Property> spec = PropertySpecification.getPropertyByAiCampaignTopic(propertyId, urlForAiSystemsFound, urlForAiSystemsFoundLastUpdate);
        return propertyRepository.findOne(spec);
    }

    public List<Property> findProperties(List<Integer> countryIds, Date urlForAiSystemsFoundLastUpdate) {
        Boolean urlForAiSystemsFound = false;
        Specification<Property> spec = PropertySpecification.getPropertyListByAiCampaignTopic(countryIds, urlForAiSystemsFound, urlForAiSystemsFoundLastUpdate);
        return propertyRepository.findAll(spec);
    }



}
