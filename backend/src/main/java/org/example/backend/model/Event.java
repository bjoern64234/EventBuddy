package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@With
@Builder
public record Event(String id, String name, String location, boolean isIndoor, LocalDateTime date, double totalCost, double remainingCost, String imageUrl) {
}
