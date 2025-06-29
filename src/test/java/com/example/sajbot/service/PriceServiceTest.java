package com.example.sajbot.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.web.client.ExpectedCount;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class PriceServiceTest {
    @Test
    void readsPricesFromApi() throws Exception {
        RestTemplate template = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        String url = "http://test/prices";
        server.expect(ExpectedCount.times(4), requestTo(url))
                .andRespond(withSuccess("{\"importPrice\":0.12,\"exportPrice\":0.34,\"importPrices\":[0.1,0.2],\"exportPrices\":[0.3,0.4]}", MediaType.APPLICATION_JSON));

        PriceService service = new PriceService(url);
        // inject RestTemplate using reflection for simplicity
        java.lang.reflect.Field f = PriceService.class.getDeclaredField("restTemplate");
        f.setAccessible(true);
        f.set(service, template);

        assertEquals(0.12, service.getCurrentImportPrice(), 1e-6);
        assertEquals(0.34, service.getCurrentExportPrice(), 1e-6);
        assertEquals(java.util.List.of(0.1, 0.2), service.getImportPriceForecast());
        assertEquals(java.util.List.of(0.3, 0.4), service.getExportPriceForecast());
        server.verify();
    }
}
