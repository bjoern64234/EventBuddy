package org.example.backend.dto.participant;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ParticipantRequestDTO(String name, String email, String profileImageUrl) {
}
