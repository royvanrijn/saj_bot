package com.example.sajbot;

import com.example.sajbot.model.SajRealtimeData;
import com.example.sajbot.service.SajInverterService;

/**
 * Simple command line tool to verify connectivity with the SAJ inverter.
 *
 * Usage: mvn -q -DskipTests package && \
 *        java -cp target/sajbot-0.0.1-SNAPSHOT.jar com.example.sajbot.SajConnectionTest
 *
 * Optionally specify SAJ_HOST and SAJ_PORT environment variables to override
 * the default host and port configured in application.yml.
 */
public class SajConnectionTest {
    public static void main(String[] args) {
        String host = System.getenv().getOrDefault("SAJ_HOST", "127.0.0.1");
        int port = Integer.parseInt(System.getenv().getOrDefault("SAJ_PORT", "502"));

        SajInverterService service = new SajInverterService(host, port);
        try {
            SajRealtimeData data = service.readRealtimeData();
            System.out.println("Connected to SAJ inverter at " + host + ":" + port);
            System.out.println("Power: " + data.getPower() + " W");
            System.out.println("PV1 Voltage: " + data.getPv1volt() + " V");
            System.out.println("Timestamp: " + data.getDatetime());
        } catch (Exception e) {
            System.err.println("Failed to read data from SAJ inverter: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
