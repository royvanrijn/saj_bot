package com.example.sajbot.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class WeatherServiceTest {
    @Test
    void predictedSolarUsesCloudCoverage() throws Exception {
        RestTemplate template = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Loc&appid=KEY&units=metric";
        server.expect(ExpectedCount.once(), requestTo(url))
                .andRespond(withSuccess("{\"list\":[{\"clouds\":{\"all\":20}}]}", MediaType.APPLICATION_JSON));

        WeatherService service = new WeatherService("KEY", "Loc");
        java.lang.reflect.Field f = WeatherService.class.getDeclaredField("restTemplate");
        f.setAccessible(true);
        f.set(service, template);

        assertEquals(80.0, service.getPredictedSolar(), 1e-6);
        server.verify();
    }

    @Test
    void hourlySolarForecastParsesValues() throws Exception {
        RestTemplate template = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Loc&appid=KEY&units=metric";
        server.expect(ExpectedCount.once(), requestTo(url))
                .andRespond(withSuccess("{\"list\":[{\"clouds\":{\"all\":10}},{\"clouds\":{\"all\":20}}]}", MediaType.APPLICATION_JSON));

        WeatherService service = new WeatherService("KEY", "Loc");
        java.lang.reflect.Field f = WeatherService.class.getDeclaredField("restTemplate");
        f.setAccessible(true);
        f.set(service, template);

        assertArrayEquals(new double[] {90.0, 80.0}, service.getHourlySolarForecast());
        server.verify();
    }
}
