package com.property.campaign.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CampaignReportCreationScheduler {

   // @Scheduled(fixedDelayString = "${property.scheduler.fixed-delay}")
    public void runCampaignReportCreationJob() throws Exception {
        log.info("Starting Campaign Report Creation Scheduler...");

        try {
            // TODO: Add campaign report creation logic here

            log.info("Campaign Report Creation completed successfully.");
        } catch (Exception e) {
            log.error("Error occurred during Campaign Report Creation Scheduler: {}", e.getMessage(), e);
            throw e;
        }
    }

}
