package org.example.backend.dto.event;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record EventResponseDTO(String id, String name, String location, boolean isIndoor, double totalCost, double remainingCost, String imageUrl) {
}
