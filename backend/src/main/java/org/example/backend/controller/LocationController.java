package org.example.backend.controller;

import org.example.backend.dto.location.GeolocationDTO;
import org.example.backend.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public GeolocationDTO getGeolocation(@RequestParam String q) {
        return this.locationService.getGeolocation(q);
    }
}
