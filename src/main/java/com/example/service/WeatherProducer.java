package com.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.model.WeatherReport;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherProducer {

    private static final String TOPIC = "weather-topic";
    private final KafkaTemplate<String, WeatherReport> kafkaTemplate;
    private final WeatherGenerationService weatherGenerationService;

    @Scheduled(fixedRate = 2000)
    public void produceWeatherData() {
        WeatherReport report = new WeatherReport(
                weatherGenerationService.generateRandomTemperature(),
                weatherGenerationService.generateRandomCity(),
                weatherGenerationService.generateRandomWeather()
        );

        kafkaTemplate.send(TOPIC, report.getCity().name(), report);
        log.info("Отправлен отчет о погоде: {}", report);
    }
}
