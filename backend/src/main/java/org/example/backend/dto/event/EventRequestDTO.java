package org.example.backend.dto.event;

import java.time.LocalDateTime;

public record EventRequestDTO(String name, boolean isIndoor, LocalDateTime date, double totalCost, String imageUrl) {
}
