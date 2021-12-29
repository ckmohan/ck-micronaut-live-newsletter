package io.micronaut.ck.live.services;

import io.micronaut.ck.live.model.Email;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public interface EmailSender {
    void sendEmail(@NonNull @NotBlank @Valid Email email);
}
