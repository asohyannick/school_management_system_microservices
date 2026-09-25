package com.ecol.authService.dto.sendPasswordlessLinkDto;

import jakarta.validation.constraints.NotBlank;

public record SendPasswordlessLinkDto(
        @NotBlank(message = "Email is required")
    String email
) { }
