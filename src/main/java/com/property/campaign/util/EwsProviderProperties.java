package com.property.campaign.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "provider.ews-api")
public class EwsProviderProperties {
    private Map<String, String> host = new HashMap<>();
    private Map<String, String> token = new HashMap<>();

    public Map<String, String> getHost() {
        return host;
    }

    public void setHost(Map<String, String> host) {
        this.host = host;
    }

    public Map<String, String> getToken() {
        return token;
    }

    public void setToken(Map<String, String> token) {
        this.token = token;
    }

    public EwsApiConfig getProviderConfig(String providerName) {
        EwsApiConfig config = new EwsApiConfig();
        config.setHost(host.get(providerName));
        config.setToken(token.get(providerName));
        return config;
    }

    public static class EwsApiConfig {
        private String host;
        private String token;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}