package com.example.sajbot.service;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrainServiceTest {
    @Test
    void evaluateStoresDataAndRespectsLimits() {
        WeatherService weather = mock(WeatherService.class);
        PriceService prices = mock(PriceService.class);

        int hour = java.time.LocalDateTime.now().getHour();
        double[] solar = new double[24];
        double[] imports = new double[24];
        double[] exports = new double[24];
        java.util.Arrays.fill(solar, 0);
        java.util.Arrays.fill(imports, 0.5);
        java.util.Arrays.fill(exports, 0.1);
        solar[hour] = 100.0;
        imports[hour] = 0.05;
        exports[(hour + 1) % 24] = 1.0;

        when(weather.getHourlySolarForecast()).thenReturn(solar);
        when(prices.getHourlyImportPrices()).thenReturn(imports);
        when(prices.getHourlyExportPrices()).thenReturn(exports);

        BatteryService battery = new BatteryService();
        DataPointRepository repo = mock(DataPointRepository.class);

        BrainService brain = new BrainService(weather, prices, battery, repo);
        double before = battery.getBatteryLevel();
        brain.evaluate();

        assertTrue(battery.getBatteryLevel() >= before);
        assertTrue(battery.getBatteryLevel() <= 100 && battery.getBatteryLevel() >= 0);
        verify(repo).save(any(DataPoint.class));
    }
}
