package io.micronaut.ck.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface ConfirmationCodeVerifier {
    boolean verify(@NonNull @NotBlank String token);
}
