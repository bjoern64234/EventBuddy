package org.example.backend.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@With
@Builder
@Document("Event")
public record Event(String id, String name, String location, boolean isIndoor, LocalDateTime date, double totalCost, double remainingCost, String imageUrl, Set<String> participantsIds, List<Task> tasks) {
}
