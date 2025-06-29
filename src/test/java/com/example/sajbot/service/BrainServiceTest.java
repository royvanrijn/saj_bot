package com.example.sajbot.service;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrainServiceTest {
    @Test
    void evaluateStoresDataPoint() {
        WeatherService weather = mock(WeatherService.class);
        when(weather.getPredictedSolar()).thenReturn(10.0);
        PriceService prices = mock(PriceService.class);
        when(prices.getCurrentImportPrice()).thenReturn(0.10);
        when(prices.getCurrentExportPrice()).thenReturn(0.40);
        BatteryService battery = new BatteryService();
        DataPointRepository repo = mock(DataPointRepository.class);

        BrainService brain = new BrainService(weather, prices, battery, repo);
        brain.evaluate();

        assertTrue(battery.getBatteryLevel() <= 100 && battery.getBatteryLevel() >= 0);
        verify(repo).save(any(DataPoint.class));
    }
}
