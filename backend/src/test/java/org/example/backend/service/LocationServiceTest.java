package org.example.backend.service;

import org.example.backend.dto.location.GeolocationDTO;
import org.example.backend.exceptions.location.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private LocationService locationService;

    private GeolocationDTO geolocationDTO;

    @BeforeEach
    void setUp() {
        geolocationDTO = GeolocationDTO.builder()
                .name("hamburg").lat("53.5501721").lon("10.0013165").build();
    }

    @Test
    void getGeolocation_ShouldReturnGeolocationDTO() {
        //Given
        GeolocationDTO[] dtoArray = new GeolocationDTO[]{geolocationDTO};

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/search?q={q}&format=json&limit=1", "Hamburg"))
                .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(GeolocationDTO[].class)).thenReturn(dtoArray);

        // When
        GeolocationDTO result = locationService.getGeolocation("Hamburg");

        // Then
        assertNotNull(result);
        assertEquals("hamburg", result.name());
        assertEquals("53.5501721", result.lat());
        assertEquals("10.0013165", result.lon());
    }

    @Test
    void getGeolocation_ShouldThrowLocationNotFoundException_WhenResponseIsNull() {
        // Given
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/search?q={q}&format=json&limit=1", "Hamburg"))
                .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(GeolocationDTO[].class)).thenReturn(null);

        // When & Then
        assertThrows(LocationNotFoundException.class, () -> locationService.getGeolocation("Hamburg"));
    }

    @Test
    void getGeolocation_ShouldThrowLocationNotFoundException_WhenResponseIsEmpty() {
        // Given
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/search?q={q}&format=json&limit=1", "Hamburg"))
                .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(GeolocationDTO[].class)).thenReturn(new GeolocationDTO[]{});

        // When & Then
        assertThrows(LocationNotFoundException.class, () -> locationService.getGeolocation("Hamburg"));
    }
}