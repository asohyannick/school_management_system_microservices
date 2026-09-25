package com.ecol.authService.dto.verifyPasswordlessToken;
import jakarta.validation.constraints.NotBlank;
public record VerifyPasswordlessToken(
        @NotBlank(message = "Valid token must be provided")
        String verifyMagicLinkToken
) { }
