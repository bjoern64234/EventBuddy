package org.example.backend.dto.location;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record GeolocationDTO(String name, String lat, String lon) {
}
