package com.aome.todoist_mp_api.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtConfig(
        @NotNull String secret,
        @NotNull String issuer,
        @NotNull Long expiration
) {
    public JwtConfig {
        Objects.requireNonNull(secret, "JWT secret cannot be null");
        Objects.requireNonNull(issuer, "JWT issuer cannot be null");
        Objects.requireNonNull(expiration, "JWT expiration cannot be null");

        if (secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }
        if (issuer.isBlank()) {
            throw new IllegalArgumentException("JWT issuer cannot be null or empty");
        }
        if (expiration <= 0) {
            throw new IllegalArgumentException("JWT expiration must be a positive number");
        }
    }
}
