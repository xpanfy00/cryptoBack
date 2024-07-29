package org.example.cryptoback.entity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HTTPClient {

    private final CloseableHttpClient httpClient;
    private final String baseUrl;
    private final String apiKey;

    public HTTPClient(@Value("${cmc.api.base-url}") String baseUrl, @Value("${cmc.api.key}") String apiKey) {
        this.httpClient = HttpClients.createDefault();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
}