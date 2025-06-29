package com.example.sajbot.controller;

import com.example.sajbot.service.BatteryService;
import com.example.sajbot.service.PriceService;
import com.example.sajbot.service.SajInverterService;
import com.example.sajbot.service.WeatherService;
import com.example.sajbot.repository.DataPointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SajBotController.class)
class SajBotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatteryService batteryService;
    @MockBean
    private DataPointRepository repository;
    @MockBean
    private SajInverterService sajInverterService;
    @MockBean
    private WeatherService weatherService;
    @MockBean
    private PriceService priceService;

    @Test
    void summaryCombinesServices() throws Exception {
        when(batteryService.getBatteryLevel()).thenReturn(70.0);
        when(priceService.getCurrentImportPrice()).thenReturn(0.11);
        when(priceService.getCurrentExportPrice()).thenReturn(0.33);
        when(weatherService.getPredictedSolar()).thenReturn(90.0);

        mockMvc.perform(get("/api/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryLevel").value(70.0))
                .andExpect(jsonPath("$.importPrice").value(0.11))
                .andExpect(jsonPath("$.exportPrice").value(0.33))
                .andExpect(jsonPath("$.predictedSolar").value(90.0));
    }
}
