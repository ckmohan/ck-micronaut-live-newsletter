package io.micronaut.ck.live.data;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface ConfirmationService {
    void confirm(@NonNull @NotBlank @Email String email);
}
