package org.example.backend.dto.event;

public record EventResponseDTO(String id, String name, String location, boolean isIndoor, double totalCost, double remainingCost, String imageUrl) {
}
