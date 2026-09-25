package com.ecol.authService.dto.blockAccountRequestDTO;
import jakarta.validation.constraints.NotBlank;
public record BlockAccountRequestDto(
		@NotBlank(message = "Reason is required")
		String reason
) {}