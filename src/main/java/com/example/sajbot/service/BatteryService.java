package com.example.sajbot.service;

import org.springframework.stereotype.Service;

@Service
public class BatteryService {
    private double level = 50.0; // start with 50%

    public double getBatteryLevel() {
        return level;
    }

    public void charge(double amount) {
        level = Math.min(100, level + amount);
    }

    public void discharge(double amount) {
        level = Math.max(0, level - amount);
    }
}
