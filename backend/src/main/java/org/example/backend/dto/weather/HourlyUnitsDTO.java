package org.example.backend.dto.weather;

public record HourlyUnitsDTO(
        String time,
        String temperature_2m,
        String relative_humidity_2m,
        String wind_speed_10m
) {
}
