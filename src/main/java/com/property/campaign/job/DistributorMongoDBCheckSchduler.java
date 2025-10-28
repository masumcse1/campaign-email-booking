package com.property.campaign.job;


import com.property.campaign.restclient.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DistributorMongoDBCheckSchduler {

    private final RestClient restClient;

    @Value("${distributor-mongo.access-url}")
    String accessUrlDistributorMongo;

    @Value("${distributor-mongo.health-check-endpoint}")
    String healthCheckEndpointDistributorMongo;

    @Scheduled(cron = "*/30 * * * * *") // every 30 seconds
    public void run() {
        boolean success = restClient.getHealthCheckDistributorMongo(accessUrlDistributorMongo + healthCheckEndpointDistributorMongo);
    }

}
