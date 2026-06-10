package org.example.backend.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Task(String id, String title, Boolean completed) {
}
