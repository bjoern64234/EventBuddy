package org.example.backend.service;

import org.example.backend.dto.location.GeolocationDTO;
import org.example.backend.exceptions.location.LocationNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LocationService {

    private final RestClient locationRestClient;

    public LocationService(@Qualifier("locationRestClient") RestClient restClient) {
        this.locationRestClient = restClient;
    }

    public GeolocationDTO getGeolocation(String q) {
        GeolocationDTO[] locationResponse = this.locationRestClient.get().uri("/search?q={q}&format=json&limit=1", q).retrieve().body(GeolocationDTO[].class);

        if (locationResponse == null || locationResponse.length == 0) {
            throw new LocationNotFoundException(q);
        }

        return locationResponse[0];
    }
}
