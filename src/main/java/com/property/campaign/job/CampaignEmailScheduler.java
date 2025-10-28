package com.property.campaign.job;

import com.property.campaign.common.dto.AccessTokenDirectus;
import com.property.campaign.common.dto.AccessTokenSupplier;
import com.property.campaign.common.dto.CampaignEmailBooking;
import com.property.campaign.common.dto.EmailDataDirectus;
import com.property.campaign.common.rdo.EmailResultRdo;
import com.property.campaign.common.restclientdto.PropertySearchResponse;
import com.property.campaign.exception.DataNotFoundException;
import com.property.campaign.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CampaignEmailScheduler {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private DirectusDataService directusDataService;

    @Autowired
    private PropertiesSearchService propertiesSearchService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private EmailCreatorService emailCreatorService;

    @Value("${campaignScheduleCron.flag}")
    private boolean campaignScheduleCron;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final int lastUpdateAfterDate = 360;

    private static final String Temp_Campain_Topic = "AI-powered Booking Engine";

    @Scheduled(fixedDelayString = "${campaign.scheduler.fixed-delay}")
    public void createEmailCampaignJob() {
        log.info("Application environment: {}", applicationName);
        log.info("Starting Scheduled Task for Create Campaign Email...");

        try {
            if (!campaignScheduleCron) {
                log.info("Campaign schedule cron flag is false. Skipping execution.");
                return;
            }

            log.info("Campaign schedule cron flag is true. Proceeding with execution.");

            AccessTokenDirectus directusToken = tokenService.getTokenFromDirectus();
            AccessTokenSupplier supplierToken = tokenService.getTokenFromSuppliers();

            List<EmailDataDirectus> dataFromDirectusApi =   directusDataService.getDataFromDirectusApi(directusToken.getAccessToken());

            List<EmailDataDirectus> directusCampaignList =   dataFromDirectusApi.stream().filter(m->m.getTopic().equals(Temp_Campain_Topic)
                    && Objects.nonNull(m.getCountry()) && Objects.nonNull(m.getCampaignActivation()) && m.getCampaignActivation()
                    && Objects.nonNull(m.getEmailLimitation()) &&  Objects.nonNull(m.getTemplate())).toList();

            List<EmailDataDirectus> directusCampaignListWithGeoCode = directusCampaignList.stream()
                    .filter(data -> data.getLatitude() != null && data.getLongitude() != null)
                    .collect(Collectors.toList());

            List<EmailDataDirectus> directusCampaignListWithCountry_old = directusCampaignList.stream()
                    .filter(data -> data.getLatitude() == null || data.getLongitude() == null)
                    .collect(Collectors.toList());

            log.info("Step 1: Directus campaigns with geocode: {}", directusCampaignListWithGeoCode.size());

            List<EmailDataDirectus> directusCampaignListWithCountry = directusCampaignListWithCountry_old.stream().filter(m->m.getId() == 2).toList();

            // Geocode-wise property search
            List<PropertySearchResponse> geocodeWisePropertyList = propertiesSearchService.getPropertiesByGeocodeAndRadius(directusCampaignListWithGeoCode);
            log.info("Step 2: Properties found by geocode: {}", geocodeWisePropertyList.size());

            List<CampaignEmailBooking> propertyListWiseGeocode = propertyService.getSupplierDataByGeocode(
                    directusCampaignListWithGeoCode,
                    supplierToken.getAccessToken(),
                    lastUpdateAfterDate,
                    geocodeWisePropertyList
            );
            log.info("Step 3: Supplier data for geocode properties: {}", propertyListWiseGeocode.size());

            createScheduledCampaignEmail(directusCampaignListWithGeoCode, propertyListWiseGeocode);
            log.info("Step 4: Campaign emails created for geocode search.");

            // Country-wise property search
            List<CampaignEmailBooking> propertyDataWithCountry = propertyService.getSupplierData(
                    supplierToken.getAccessToken(),
                    lastUpdateAfterDate,
                    directusCampaignListWithCountry
            );
            log.info("Step 5: Supplier data for country properties: {}", propertyDataWithCountry.size());

            createScheduledCampaignEmail(directusCampaignListWithCountry, propertyDataWithCountry);
            log.info("Step 6: Campaign emails created for country search.");

            int totalCampaignEmailLimitation = dataFromDirectusApi.stream()
                    .filter(EmailDataDirectus::getCampaignActivation)
                    .mapToInt(e -> e.getEmailLimitation() != null ? e.getEmailLimitation() : 0)
                    .sum();
            log.info("Total campaign email limitation: {}", totalCampaignEmailLimitation);

            log.info("Scheduled Task completed successfully for Create Campaign Email!");

        } catch (DataNotFoundException e) {
            log.error("Data not found during campaign creation: {}", e.getMessage(), e);
        }catch (Exception e) {
            log.error("Unexpected error in campaign email scheduler: {}", e.getMessage(), e);
        } finally {
            log.info("Scheduled Task finished (success or error).");
        }
    }


    private void createScheduledCampaignEmail(List<EmailDataDirectus> dataFromDirectus, List<CampaignEmailBooking> propertyList) {

        List<EmailResultRdo> emailForBooking = emailCreatorService.getEmailForBooking(dataFromDirectus, propertyList, tokenService.getTokenFromSuppliers().getAccessToken());

        log.info("Number of campaign messages to save: {}", emailForBooking.size());

        supplierService.saveDataToSupplierApi(tokenService.getTokenFromSuppliers().getAccessToken(), emailForBooking);
    }
}
