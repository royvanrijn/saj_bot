package com.example.sajbot.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BatteryServiceTest {
    @Test
    void chargeAndDischargeRespectLimits() {
        BatteryService service = new BatteryService();
        service.charge(60);
        assertEquals(100.0, service.getBatteryLevel());
        service.discharge(150);
        assertEquals(0.0, service.getBatteryLevel());
    }
}
