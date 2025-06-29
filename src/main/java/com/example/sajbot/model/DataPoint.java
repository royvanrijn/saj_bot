package com.example.sajbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class DataPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private double batteryLevel;
    private double importPrice;
    private double exportPrice;
    private double predictedSolar;

    public DataPoint() {}

    public DataPoint(LocalDateTime timestamp, double batteryLevel, double importPrice, double exportPrice, double predictedSolar) {
        this.timestamp = timestamp;
        this.batteryLevel = batteryLevel;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.predictedSolar = predictedSolar;
    }

    // getters and setters omitted for brevity
    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public double getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(double batteryLevel) { this.batteryLevel = batteryLevel; }
    public double getImportPrice() { return importPrice; }
    public void setImportPrice(double importPrice) { this.importPrice = importPrice; }
    public double getExportPrice() { return exportPrice; }
    public void setExportPrice(double exportPrice) { this.exportPrice = exportPrice; }
    public double getPredictedSolar() { return predictedSolar; }
    public void setPredictedSolar(double predictedSolar) { this.predictedSolar = predictedSolar; }
}
