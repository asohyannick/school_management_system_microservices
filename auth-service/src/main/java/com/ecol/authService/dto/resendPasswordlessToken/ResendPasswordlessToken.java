package com.ecol.authService.dto.resendPasswordlessToken;

import jakarta.validation.constraints.NotBlank;

public record ResendPasswordlessToken(
        @NotBlank(message = "Email is required")
         String email
) { }
