package com.example.sajbot.service;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BrainService {
    private final WeatherService weatherService;
    private final PriceService priceService;
    private final BatteryService batteryService;
    private final DataPointRepository repository;

    public BrainService(WeatherService weatherService,
                        PriceService priceService,
                        BatteryService batteryService,
                        DataPointRepository repository) {
        this.weatherService = weatherService;
        this.priceService = priceService;
        this.batteryService = batteryService;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 3600000)
    public void evaluate() {
        double solarForecast = weatherService.getPredictedSolar();
        double importPrice = priceService.getCurrentImportPrice();
        double exportPrice = priceService.getCurrentExportPrice();

        // Simple algorithm: if solar forecast is low and import price is low -> charge
        if (solarForecast < 50 && importPrice < 0.20) {
            batteryService.charge(10);
        }
        // If export price high and battery high -> discharge
        if (exportPrice > 0.30 && batteryService.getBatteryLevel() > 20) {
            batteryService.discharge(10);
        }
        repository.save(new DataPoint(LocalDateTime.now(), batteryService.getBatteryLevel(), importPrice, exportPrice, solarForecast));
    }
}
