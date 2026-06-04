package org.example.backend.dto.event;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@With
@Builder
public record EventRequestDTO(String name, boolean isIndoor, LocalDateTime date, double totalCost, String imageUrl) {
}
