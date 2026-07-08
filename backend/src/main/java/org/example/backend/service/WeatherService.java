package org.example.backend.service;

import org.example.backend.dto.location.GeolocationDTO;
import org.example.backend.dto.weather.ForecastResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {

    private final RestClient weatherRestClient;
    private final LocationService locationService;

    public WeatherService(@Qualifier("weatherRestClient") RestClient weatherRestClient, LocationService locationService) {
        this.weatherRestClient = weatherRestClient;
        this.locationService = locationService;
    }

    public ForecastResponseDTO getForecast(String city) {
        GeolocationDTO geolocation = this.locationService.getGeolocation(city);
        String lat = geolocation.lat();
        String lng = geolocation.lon();

        return this.weatherRestClient.get().uri("/forecast?latitude={lat}&longitude={lng}&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m", lat, lng).retrieve().body(ForecastResponseDTO.class);
    }
}
