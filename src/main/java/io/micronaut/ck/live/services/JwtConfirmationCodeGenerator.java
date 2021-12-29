package io.micronaut.ck.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import jakarta.inject.Singleton;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator {
    public static final String CLAIM_EMAIL = "email";
    private final TokenGenerator tokenGenerator;

    public JwtConfirmationCodeGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    @NonNull
    public Optional<String> generate(@NonNull String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.EXPIRATION_TIME, Date.from(Instant.now().plus(3600, ChronoUnit.SECONDS)));
        claims.put(CLAIM_EMAIL, email);
        return tokenGenerator.generateToken(claims);
    }
}
