package org.example.backend.controller;

import org.example.backend.dto.weather.ForecastResponseDTO;
import org.example.backend.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecast")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ForecastResponseDTO getForecast(@RequestParam String city) {
        return this.weatherService.getForecast(city);
    }
}
