package io.micronaut.ck.live.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator, ConfirmationCodeVerifier {

    private static final Logger LOG = LoggerFactory.getLogger(JwtConfirmationCodeGenerator.class);
    private static final String CLAIM_EMAIL = "email";
    private final TokenGenerator tokenGenerator;
    private final JwtValidator jwtValidator;

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
    public Optional<String> verify(String token) {
        Optional<JWT> optionalJWT = jwtValidator.validate(token, null);
        if (optionalJWT.isPresent()) {
            JWT jwt = optionalJWT.get();
            try {
                Object claim = jwt.getJWTClaimsSet().getClaim(CLAIM_EMAIL);
                if (Objects.isNull(claim)) {
                    return Optional.empty();
                }
                return Optional.of(claim.toString());
            } catch (ParseException e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Could not get the Claims", e);
                }
            }
        }
        return Optional.empty();
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
