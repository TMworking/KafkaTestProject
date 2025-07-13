package com.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.model.City;
import com.example.model.WeatherCondition;
import com.example.model.WeatherReport;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherConsumer {

    private final Map<City, List<WeatherReport>> cityReports = new ConcurrentHashMap<>();
    private final Map<City, Integer> sunnyDaysCount = new ConcurrentHashMap<>();
    private final Map<City, Integer> rainyDaysCount = new ConcurrentHashMap<>();

    @KafkaListener(topics = "weather-topic", groupId = "my-group")
    public void consume(WeatherReport report) {
        log.info("Получен отчет о погоде: {}", report);

        cityReports.computeIfAbsent(report.getCity(), k -> new ArrayList<>()).add(report);

        if (report.getWeatherCondition().equals(WeatherCondition.SUNNY)) {
            sunnyDaysCount.merge(report.getCity(), 1, Integer::sum);
        } else if (report.getWeatherCondition() == WeatherCondition.RAINY) {
            rainyDaysCount.merge(report.getCity(), 1, Integer::sum);
        }

        // Периодически выводим аналитику
        if (cityReports.values().stream().mapToInt(List::size).sum() % 10 == 0) {
            printAnalytics();
        }
    }

    private void printAnalytics() {
        log.info("\n===== Аналитика погоды =====");

        sunnyDaysCount.forEach((city, count) ->
                log.info("{}: {} солнечных дней", city.getDescription(), count));

        rainyDaysCount.forEach((city, count) ->
                log.info("{}: {} дождливых дней", city.getDescription(), count));

        cityReports.forEach((city, reports) -> {
            double avgTemp = reports.stream()
                    .mapToInt(WeatherReport::getTemperature)
                    .average()
                    .orElse(0);
            log.info("{}: средняя температура {}", city.getDescription(), avgTemp);
        });
    }
}
