package io.micronaut.ck.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ConfirmationCodeVerifier {
    Optional<String> verify(@NonNull @NotBlank String token);
}
