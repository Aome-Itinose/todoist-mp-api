package com.aome.todoist_mp_api.util;

import com.aome.todoist_mp_api.config.JwtConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfig config;

    public String validateAndGetChatId(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(config.secret()))
                .withIssuer(config.issuer())
                .build()
                .verify(token)
                .getSubject();
    }
}
