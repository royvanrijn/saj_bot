package com.example.sajbot.controller;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import com.example.sajbot.service.BatteryService;
import com.example.sajbot.service.SajInverterService;
import com.example.sajbot.model.SajRealtimeData;
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

    public SajBotController(BatteryService batteryService,
                            DataPointRepository repository,
                            SajInverterService sajInverterService) {
        this.batteryService = batteryService;
        this.repository = repository;
        this.sajInverterService = sajInverterService;
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
}
