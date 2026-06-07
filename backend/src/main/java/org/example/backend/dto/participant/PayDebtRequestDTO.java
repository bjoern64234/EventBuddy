package org.example.backend.dto.participant;

import jakarta.validation.constraints.Positive;

public record PayDebtRequestDTO(
        @Positive(message = "The amount must be positive")
        Double debt
) {}