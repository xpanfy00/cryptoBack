package org.example.cryptoback.controller;

import org.example.cryptoback.service.CMCHTTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/crypto")
@CrossOrigin(origins = "http://localhost:5173")
public class CryptoController {

    private final CMCHTTPClient cmcHttpClient;

    @Autowired
    public CryptoController(CMCHTTPClient cmcHttpClient) {
        this.cmcHttpClient = cmcHttpClient;
    }

    @GetMapping("/listings")
    public String getListings() {
        try {
            return cmcHttpClient.getListings();
        } catch (IOException | URISyntaxException e) {
            // Обработка ошибок, возможно логгирование или возвращение кода ошибки
            e.printStackTrace();
            return "Error fetching listings";
        }
    }

    @GetMapping("/currency/{id}")
    public String getCurrency(@PathVariable("id") int currencyId) {
        try {
            return cmcHttpClient.getCurrency(currencyId);
        } catch (IOException | URISyntaxException e) {
            // Обработка ошибок, возможно логгирование или возвращение кода ошибки
            e.printStackTrace();
            return "Error fetching currency";
        }
    }
}
