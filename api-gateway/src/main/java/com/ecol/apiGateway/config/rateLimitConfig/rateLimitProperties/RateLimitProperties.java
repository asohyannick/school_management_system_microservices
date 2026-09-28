package com.ecol.apiGateway.config.rateLimitConfig.rateLimitProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(
        prefix = "gateway.rate-limit"
)
public record RateLimitProperties(
        @DefaultValue("500") int capacity,
        @DefaultValue("5m") Duration window
) {}