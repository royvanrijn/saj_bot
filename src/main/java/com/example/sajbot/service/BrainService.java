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
        double[] solar = weatherService.getHourlySolarForecast();
        double[] imports = priceService.getHourlyImportPrices();
        double[] exports = priceService.getHourlyExportPrices();

        int hours = Math.min(Math.min(solar.length, imports.length), exports.length);
        if (hours == 0) {
            solar = new double[] { weatherService.getPredictedSolar() };
            imports = new double[] { priceService.getCurrentImportPrice() };
            exports = new double[] { priceService.getCurrentExportPrice() };
            hours = 1;
        }

        int bestChargeHour = 0;
        int bestDischargeHour = 0;
        double bestChargeScore = Double.MAX_VALUE;
        double bestDischargePrice = Double.MIN_VALUE;
        for (int i = 0; i < hours; i++) {
            double score = imports[i] - solar[i] / 100.0;
            if (score < bestChargeScore) {
                bestChargeScore = score;
                bestChargeHour = i;
            }
            if (exports[i] > bestDischargePrice) {
                bestDischargePrice = exports[i];
                bestDischargeHour = i;
            }
        }

        int currentHour = LocalDateTime.now().getHour() % hours;

        if (currentHour == bestChargeHour) {
            batteryService.charge(10);
        } else if (currentHour == bestDischargeHour && batteryService.getBatteryLevel() > 20) {
            batteryService.discharge(10);
        }

        if (currentHour >= 0 && currentHour < 6 && batteryService.getBatteryLevel() < 20) {
            batteryService.charge(20 - batteryService.getBatteryLevel());
        }

        repository.save(new DataPoint(LocalDateTime.now(),
                batteryService.getBatteryLevel(),
                imports[Math.min(currentHour, imports.length - 1)],
                exports[Math.min(currentHour, exports.length - 1)],
                solar[Math.min(currentHour, solar.length - 1)]));
    }
}
