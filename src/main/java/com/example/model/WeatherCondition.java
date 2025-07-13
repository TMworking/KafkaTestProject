package com.example.model;

import java.util.Random;

public enum WeatherCondition {
    SUNNY("Солнечно"),
    CLOUDLY("Облачно"),
    RAINY("Дождливо");

    private final String description;

    WeatherCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static WeatherCondition generateRandomWeather() {
        WeatherCondition[] values = WeatherCondition.values();
        return values[new Random().nextInt(values.length)];
    }
}
