package com.aome.todoist_mp_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtConfig(
        String secret,
        String issuer,
        Long expiration
) {}
