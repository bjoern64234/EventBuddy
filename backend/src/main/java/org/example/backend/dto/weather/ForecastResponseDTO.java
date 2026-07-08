package org.example.backend.dto.weather;

public record ForecastResponseDTO(
        String latitude,
        String longitude,
        String timezone,
        CurrentUnitsDTO current_units,
        CurrentDTO current,
        HourlyUnitsDTO hourly_units,
        HourlyDTO hourly
) {
}
