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

    /**
     * Retrieve hourly import prices for the next 24 hours.
     */
    public double[] getHourlyImportPrices() {
        try {
            var node = restTemplate.getForObject(priceUrl, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null && node.has("hourlyImport")) {
                var arr = node.get("hourlyImport");
                int len = Math.min(24, arr.size());
                double[] result = new double[len];
                for (int i = 0; i < len; i++) {
                    result[i] = arr.get(i).asDouble();
                }
                return result;
            }
        } catch (Exception ignored) {}
        return new double[0];
    }

    /**
     * Retrieve hourly export prices for the next 24 hours.
     */
    public double[] getHourlyExportPrices() {
        try {
            var node = restTemplate.getForObject(priceUrl, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null && node.has("hourlyExport")) {
                var arr = node.get("hourlyExport");
                int len = Math.min(24, arr.size());
                double[] result = new double[len];
                for (int i = 0; i < len; i++) {
                    result[i] = arr.get(i).asDouble();
                }
                return result;
            }
        } catch (Exception ignored) {}
        return new double[0];
    }
}
