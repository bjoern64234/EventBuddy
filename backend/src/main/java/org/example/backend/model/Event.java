package org.example.backend.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@With
@Builder
@Document("Event")
public record Event(String id, String name, String location, boolean isIndoor, LocalDateTime date, double totalCost, double remainingCost, String imageUrl) {
}
