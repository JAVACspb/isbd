package com.ifmo.isdb.strattanoakmant.service.impl;

import com.ifmo.isdb.strattanoakmant.model.Employee;
import com.ifmo.isdb.strattanoakmant.model.JwtToken;
import com.ifmo.isdb.strattanoakmant.model.Login;
import com.ifmo.isdb.strattanoakmant.repository.EmployeeRepository;
import com.ifmo.isdb.strattanoakmant.repository.PositionRepository;
import com.ifmo.isdb.strattanoakmant.repository.TokenRepository;
import com.ifmo.isdb.strattanoakmant.service.ifc.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger log =
            LoggerFactory.getLogger(TokenServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final TokenRepository tokenRepository;
    private final JwtCodec jwtCodec;

    /**
     * Secret for signing JWTs. Must be >= 32 bytes for HS256.
     * Can be overridden via config (e.g. application-local.yaml) or env.
     */
    @Value("${jwt.ttlHours:12}")
    private long jwtTtlHours;


    @Override
    public JwtToken createToken(Login login) {
        log.debug(String.format("Creating token for user = %s", login.getLogin()));
        Employee employee = Optional
                .ofNullable(employeeRepository.findByLoginAndPassword(login.getLogin(), login.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Invalid login or password"));
        String role = Optional.ofNullable(positionRepository.findRoleById(employee.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Can't find role by employee id= %s ", employee.getId())));
        JwtToken token = createToken(employee.getId(), role);
        log.debug(String.format("Created token for user = %s", login.getLogin()));
        return tokenRepository.save(token);
    }

    @Cacheable(cacheNames = "tokenUser", key = "#token")
    public Employee getUserByToken(String token) {
        log.debug(String.format("Finding user by token = %s", token));
        Map<String, Object> claims = getClaims(token);
        Number userId = (Number) claims.get("uid");
        return employeeRepository
                .findById(userId.longValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        String.format("Bad token, no employee for id = %s ", userId)));
    }

    @Cacheable(cacheNames = "tokenRole", key = "#token")
    public String getRoleByToken(String token) {
        log.debug(String.format("Finding role by token = %s", token));
        Map<String, Object> claims = getClaims(token);
        return (String) claims.get("role");
    }

    private Map<String, Object> getClaims(String token) {
        String normalized = jwtCodec.normalizeToken(token);
        if (normalized == null || normalized.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization token");
        }
        Claims claims = jwtCodec.parseClaims(normalized);
        return claims;
    }

    private JwtToken createToken(Long userId, String role) {
        LocalDateTime dateTime = LocalDateTime.now();
        Instant issuedDateInstant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant expirationDateInstant = dateTime.plusHours(jwtTtlHours).atZone(ZoneId.systemDefault()).toInstant();

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("uid", userId);
        customClaims.put("role", role);

        String compact = jwtCodec.createJwt(customClaims, issuedDateInstant, expirationDateInstant);

        return new JwtToken(compact, role, LocalDateTime.now());
    }

    @Scheduled(cron = "${token.cleanup.cron:*/10 * * * * ?}")
    public void refreshTokens() {
        try {
            LocalDateTime cutoff = LocalDateTime.now().minusHours(1);
            int deleted = tokenRepository.deleteByLocalDateTimeBefore(cutoff);
            if (deleted > 0) {
                log.debug(String.format("Deleted %s expired tokens", deleted));
            }
        } catch (Exception e) {
            // On a fresh DB startup Liquibase may not have created tables yet.
            // This job must never crash the service.
            log.debug("Skipping token cleanup (DB not ready yet): " + e.getMessage());
        }
    }
}
