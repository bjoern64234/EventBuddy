package org.example.backend.dto.weather;

public record CurrentDTO(
        String time,
        String interval,
        String temperature_2m,
        String wind_speed_10m
) {
}
