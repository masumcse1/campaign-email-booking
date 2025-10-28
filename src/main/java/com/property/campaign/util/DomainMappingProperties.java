package com.property.campaign.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "directus.domain")
public class DomainMappingProperties {

    private Map<String, String> mappings = new HashMap<>();

    public Map<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public String getEwsProvider(String mailServer) {
        return mappings.get(mailServer);
    }

    // Returns "mail1" from "mail1.mappingmaster.com"
    public  String extractMailServer(String directusDomain) {
        String[] parts = directusDomain.split("\\.");
        return parts[0];
    }

}