package com.example.sajbot.model;

public class Summary {
    private final double batteryLevel;
    private final double importPrice;
    private final double exportPrice;
    private final double predictedSolar;

    public Summary(double batteryLevel, double importPrice,
                   double exportPrice, double predictedSolar) {
        this.batteryLevel = batteryLevel;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.predictedSolar = predictedSolar;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public double getPredictedSolar() {
        return predictedSolar;
    }
}
