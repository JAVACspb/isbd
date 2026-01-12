package com.ifmo.isdb.strattanoakmant.service.impl;

import com.ifmo.isdb.strattanoakmant.repository.EmployeeRepository;
import com.ifmo.isdb.strattanoakmant.repository.PositionRepository;
import com.ifmo.isdb.strattanoakmant.repository.TokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class TokenServiceImplTest {

    private static TokenServiceImpl newServiceWithSecret(String secret) throws Exception {
        JwtCodec codec = new JwtCodec();
        Field secretField = JwtCodec.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(codec, secret);

        TokenServiceImpl svc = new TokenServiceImpl(
                mock(EmployeeRepository.class),
                mock(PositionRepository.class),
                mock(TokenRepository.class),
                codec
        );
        return svc;
    }

    @Test
    void getRoleByToken_acceptsBearerPrefix() throws Exception {
        String secret = "change-me-please-change-me-please-32bytes";
        TokenServiceImpl svc = newServiceWithSecret(secret);

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", 1L);
        claims.put("role", "SELLER");

        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        assertEquals("SELLER", svc.getRoleByToken("Bearer " + jwt));
    }

    @Test
    void getUserByToken_missingToken_returns401() throws Exception {
        TokenServiceImpl svc = newServiceWithSecret("change-me-please-change-me-please-32bytes");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> svc.getUserByToken("  "));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }
}


