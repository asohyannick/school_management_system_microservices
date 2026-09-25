package com.ecol.authService.dto.unBlockAccountRequestDto;

import jakarta.validation.constraints.NotBlank;

public record UnBlockAccountRequestDto(
        @NotBlank(message = "Reason is required")
        String reason
) { }
