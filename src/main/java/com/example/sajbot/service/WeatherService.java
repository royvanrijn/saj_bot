package com.example.sajbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;
    private final String location;

    public WeatherService(@Value("${weather.api-key}") String apiKey,
                          @Value("${weather.location}") String location) {
        this.apiKey = apiKey;
        this.location = location;
    }

    public double getPredictedSolar() {
        // call OpenWeather API for simplicity
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + apiKey + "&units=metric";
        try {
            var node = restTemplate.getForObject(url, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null) {
                // Return cloud coverage for next hour as inverse solar output indicator
                return 100 - node.get("list").get(0).get("clouds").get("all").asDouble();
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * Retrieve hourly solar forecast for the next 24 hours. Each entry
     * represents the predicted solar output percentage for that hour.
     */
    public double[] getHourlySolarForecast() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + apiKey + "&units=metric";
        try {
            var node = restTemplate.getForObject(url, com.fasterxml.jackson.databind.JsonNode.class);
            if (node != null) {
                var list = node.get("list");
                int len = Math.min(24, list.size());
                double[] result = new double[len];
                for (int i = 0; i < len; i++) {
                    result[i] = 100 - list.get(i).get("clouds").get("all").asDouble();
                }
                return result;
            }
        } catch (Exception ignored) {
        }
        return new double[0];
    }
}
