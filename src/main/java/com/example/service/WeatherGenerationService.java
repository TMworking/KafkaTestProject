package com.example.service;

import com.example.model.City;
import com.example.model.WeatherCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WeatherGenerationService {

    @Value("${weather.temperature.min:0}")
    private int minTemp;

    @Value("${weather.temperature.max:35}")
    private int maxTemp;

    public City generateRandomCity() {
        return City.generateRandomCity();
    }

    public WeatherCondition generateRandomWeather() {
        return WeatherCondition.generateRandomWeather();
    }

    public int generateRandomTemperature() {
        return minTemp + new Random().nextInt(maxTemp - minTemp + 1);
    }
}
