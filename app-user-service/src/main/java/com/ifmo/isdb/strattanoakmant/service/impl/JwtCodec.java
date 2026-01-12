package com.ifmo.isdb.strattanoakmant.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtCodec {

    @Value("${jwt.secret:change-me-please-change-me-please-32bytes}")
    private String jwtSecret;

    public String createJwt(Map<String, Object> claims, Instant issuedAt, Instant expiresAt) {
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");

        return Jwts.builder()
                .setHeader(header)
                .addClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiresAt))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims parseClaims(String token) {
        String normalized = normalizeToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(normalized)
                .getBody();
    }

    /**
     * Swagger users often paste either raw token or "Bearer <token>".
     */
    public String normalizeToken(String token) {
        if (token == null) return null;
        String trimmed = token.trim();
        if (trimmed.regionMatches(true, 0, "Bearer ", 0, "Bearer ".length())) {
            return trimmed.substring("Bearer ".length()).trim();
        }
        return trimmed;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}


