package org.example.backend.dto.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;
import org.hibernate.validator.constraints.URL;

@With
@Builder
public record ParticipantRequestDTO(
        @NotBlank(message = "The name of the event can not be blank")
        String name,
        @Email(message = "The email must be a valid email")
        String email,
        @URL(message = "The image url must be a valid url")
        String profileImageUrl
) {
}
