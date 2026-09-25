package com.ecol.authService.config.authEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuthBaseEvent {
    private String eventId = UUID.randomUUID().toString();
    private String eventType;
    private Instant timestamp = Instant.now();
    private String correlationId; // for distributed tracing
}