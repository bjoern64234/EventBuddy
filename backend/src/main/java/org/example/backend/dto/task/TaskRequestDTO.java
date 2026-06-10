package org.example.backend.dto.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record TaskRequestDTO(
         @NotBlank(message = "Title can`t be blank") String title
) {
}
