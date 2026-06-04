package org.example.backend.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@With
@Builder
public record EventRequestDTO(
        @NotBlank(message = "The name of the event can not be blank")
        String name,
        @NotNull(message = "The indoor flag can not be null")
        Boolean isIndoor,
        @NotNull(message = "The event time can not be null")
        @Future(message = "The event must be in a future time")
        LocalDateTime date,
        @Positive(message = "The total cost must be a positive number")
        double totalCost,
        @URL(message = "The image url must be a valid url")
        String imageUrl) {
}
