package org.example.backend.dto.event;

import lombok.Builder;
import lombok.With;
import org.example.backend.model.Task;

import java.util.List;
import java.util.Set;

@With
@Builder
public record EventResponseDTO(String id, String name, String location, boolean isIndoor, double totalCost, double remainingCost, String imageUrl, Set<String> participantsIds, List<Task> tasks) {
}

