package io.micronaut.ck.live.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator, ConfirmationCodeVerifier {

    private static final String CLAIM_EMAIL = "email";
    private final TokenGenerator tokenGenerator;
    private final JwtValidator jwtValidator;

    @Override
    public boolean verify(String token) {
        Optional<JWT> validate = jwtValidator.validate(token, null);
        return validate.isPresent();
    }

    public JwtConfirmationCodeGenerator(Collection<SignatureConfiguration> singatureConfigurations,
                                        Collection<EncryptionConfiguration> encryptionConfigurations,
                                        ExpirationJwtClaimsValidator expirationJwtClaimsValidator,
                                        TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
        this.jwtValidator = JwtValidator.builder()
                .withSignatures(singatureConfigurations)
                .withEncryptions(encryptionConfigurations)
                .withClaimValidators(expirationJwtClaimsValidator).build();
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
