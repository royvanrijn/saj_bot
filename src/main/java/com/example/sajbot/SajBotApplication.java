package com.example.sajbot;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class SajBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(SajBotApplication.class, args);
    }
}
