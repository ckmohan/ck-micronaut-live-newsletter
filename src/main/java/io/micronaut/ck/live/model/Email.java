package io.micronaut.ck.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Introspected
public record Email(
        @NonNull @NotBlank @javax.validation.constraints.Email
        String to,
        @Nullable String html,
        @Nullable String text) {
}
