package com.ecol.authService.dto.verifyFirebaseIdTokenDto;

import jakarta.validation.constraints.NotBlank;

public record VerifyFirebaseIdTokenDto(
        @NotBlank(message = "Firebase ID Token is required")
        String idToken
) { }
