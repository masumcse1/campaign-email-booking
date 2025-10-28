package com.property.campaign.config;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class AppConfig {

    @Value("${distributor-mongo.access-url}")
    String accessUrlDistributorMongo;


    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr=new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }


    @Bean(name = "DistributorMongoAdapter")
    public RestTemplate pooledRestTemplateDM() {
        return getRestTemplate(accessUrlDistributorMongo);
    }

    private RestTemplate getRestTemplate(String accessUrl) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(500);

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();

        return new RestTemplateBuilder().rootUri(accessUrl)
                .setConnectTimeout(Duration.ofMillis(5000))
//                .setReadTimeout(Duration.ofMillis(5000))
                .messageConverters(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter())
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

}
