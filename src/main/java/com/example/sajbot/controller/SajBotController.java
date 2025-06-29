package com.example.sajbot.controller;

import com.example.sajbot.model.DataPoint;
import com.example.sajbot.repository.DataPointRepository;
import com.example.sajbot.service.BatteryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SajBotController {
    private final BatteryService batteryService;
    private final DataPointRepository repository;

    public SajBotController(BatteryService batteryService, DataPointRepository repository) {
        this.batteryService = batteryService;
        this.repository = repository;
    }

    @GetMapping("/battery")
    public double batteryLevel() {
        return batteryService.getBatteryLevel();
    }

    @GetMapping("/data")
    public List<DataPoint> data() {
        return repository.findAll();
    }
}
