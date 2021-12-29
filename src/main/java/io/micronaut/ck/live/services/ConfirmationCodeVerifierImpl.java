package io.micronaut.ck.live.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Optional;

@Singleton
public class ConfirmationCodeVerifierImpl implements ConfirmationCodeVerifier {

    @Inject
    private final JwtValidator jwtValidator;

    public ConfirmationCodeVerifierImpl(Collection<SignatureConfiguration> singatureConfigurations,
                                        Collection<EncryptionConfiguration> encryptionConfigurations, ExpirationJwtClaimsValidator expirationJwtClaimsValidator) {
        this.jwtValidator = JwtValidator.builder()
                .withSignatures(singatureConfigurations)
                .withEncryptions(encryptionConfigurations)
                .withClaimValidators(expirationJwtClaimsValidator).build();
    }


    @Override
    public boolean verify(String token) {
        Optional<JWT> validate = jwtValidator.validate(token, null);
        return validate.isPresent();
    }
}
