package com.rizzatto.borabusao_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;

@Configuration
public class SptransConfig {

    @Bean
    RestClient sptransRestClient(
            @Value("${sptrans.api.url}") String apiUrl) {

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        HttpClient httpClient = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .build();

        return RestClient.builder()
                .baseUrl(apiUrl)
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build();
    }

    @Bean
    RestClient itinerariosRestClient(
            @Value("${itinerarios.sptrans.api.url}") String apiUrl) {

        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Origin", "https://www.sptrans.com.br")
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build();
    }
}
