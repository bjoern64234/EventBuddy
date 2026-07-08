package org.example.backend.dto.weather;

import java.util.List;

public record HourlyDTO(
        List<String> time,
        List<Double> temperature_2m,
        List<Double> relative_humidity_2m,
        List<Double> wind_speed_10m
) {
}
