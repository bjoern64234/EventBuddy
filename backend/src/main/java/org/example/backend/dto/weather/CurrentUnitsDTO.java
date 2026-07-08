package org.example.backend.dto.weather;

public record CurrentUnitsDTO(
        String time,
        String interval,
        String temperature_2m,
        String wind_speed_10m
) {
}
