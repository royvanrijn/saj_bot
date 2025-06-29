package com.example.sajbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String priceUrl;

    public PriceService(@Value("${electricity.price-url}") String priceUrl) {
        this.priceUrl = priceUrl;
    }

    public double getCurrentImportPrice() {
        try {
            var node = restTemplate.getForObject(priceUrl, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null) {
                return node.get("importPrice").asDouble();
            }
        } catch (Exception ignored) {}
        return 0.25; // fallback default
    }

    public double getCurrentExportPrice() {
        try {
            var node = restTemplate.getForObject(priceUrl, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null) {
                return node.get("exportPrice").asDouble();
            }
        } catch (Exception ignored) {}
        return 0.05; // fallback default
    }
}
