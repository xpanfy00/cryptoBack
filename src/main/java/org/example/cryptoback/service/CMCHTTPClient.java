package org.example.cryptoback.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.cryptoback.entity.HTTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CMCHTTPClient extends HTTPClient {

    public CMCHTTPClient(@Value("${cmc.api.base-url}") String baseUrl, @Value("${cmc.api.key}") String apiKey) {
        super(baseUrl, apiKey);
    }

    @Cacheable("listings")
    public String getListings() throws IOException, URISyntaxException {
        String uri = getBaseUrl() + "/v1/cryptocurrency/listings/latest";
        return makeAPICall(uri);
    }

    @Cacheable("currency")
    public String getCurrency(int currencyId) throws IOException, URISyntaxException {
        String uri = getBaseUrl() + "/v2/cryptocurrency/quotes/latest";
        URIBuilder uriBuilder = new URIBuilder(uri);
        uriBuilder.addParameter("id", String.valueOf(currencyId));
        return makeAPICall(uriBuilder.build().toString());
    }

    private String makeAPICall(String uri) throws IOException, URISyntaxException {
        URI uriObj = new URI(uri);
        HttpGet request = new HttpGet(uriObj);
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.setHeader("X-CMC_PRO_API_KEY", getApiKey());

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            } else {
                throw new IOException("No response entity");
            }
        }
    }
}
