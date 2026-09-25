package com.ecol.authService.dto.resendOTPVerificationCode;
import jakarta.validation.constraints.NotBlank;

public record ResendOTPVerificationCode(
        @NotBlank(message = "OTP code is required")
        String otpCode
) { }
