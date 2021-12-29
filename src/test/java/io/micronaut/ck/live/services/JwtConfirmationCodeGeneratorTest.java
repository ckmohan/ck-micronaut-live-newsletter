package io.micronaut.ck.live.services;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
class JwtConfirmationCodeGeneratorTest {

    @Inject
    JwtConfirmationCodeGenerator jwtConfirmationCodeGenerator;

    @Test
    void shouldGenerateJWTWithUserEmailInTheClaim() throws ParseException {
        Optional<String> jwtString = jwtConfirmationCodeGenerator.generate("tcook@apple.com");
        assertTrue(jwtString.isPresent());
        JWT jwt = JWTParser.parse(jwtString.get());
        assertTrue(jwt instanceof SignedJWT);
        assertEquals("tcook@apple.com", jwt.getJWTClaimsSet().getClaim("email"));
        assertTrue(jwt.getJWTClaimsSet().getExpirationTime().after(new Date()));
    }
}