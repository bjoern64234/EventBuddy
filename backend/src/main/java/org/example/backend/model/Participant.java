package org.example.backend.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Builder
@Document("Participant")
public record Participant(String id, String name, String email, String profileImageUrl, Double dept) {
}
