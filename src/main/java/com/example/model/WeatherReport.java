package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherReport {
    private int temperature;
    private City city;
    private WeatherCondition weatherCondition;
}
