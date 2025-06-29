package com.example.sajbot.controller;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import com.example.sajbot.service.BatteryService;
import com.example.sajbot.service.SajInverterService;
import com.example.sajbot.model.SajRealtimeData;
import com.example.sajbot.model.Summary;
import com.example.sajbot.service.PriceService;
import com.example.sajbot.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SajBotController {
    private final BatteryService batteryService;
    private final DataPointRepository repository;
    private final SajInverterService sajInverterService;
    private final WeatherService weatherService;
    private final PriceService priceService;

    public SajBotController(BatteryService batteryService,
                            DataPointRepository repository,
                            SajInverterService sajInverterService,
                            WeatherService weatherService,
                            PriceService priceService) {
        this.batteryService = batteryService;
        this.repository = repository;
        this.sajInverterService = sajInverterService;
        this.weatherService = weatherService;
        this.priceService = priceService;
    }

    @GetMapping("/battery")
    public double batteryLevel() {
        return batteryService.getBatteryLevel();
    }

    @GetMapping("/data")
    public List<DataPoint> data() {
        return repository.findAll();
    }

    @GetMapping("/inverter/realtime")
    public SajRealtimeData realtime() {
        return sajInverterService.readRealtimeData();
    }

    @GetMapping("/summary")
    public Summary summary() {
        return new Summary(
                batteryService.getBatteryLevel(),
                priceService.getCurrentImportPrice(),
                priceService.getCurrentExportPrice(),
                weatherService.getPredictedSolar()
        );
    }
}
