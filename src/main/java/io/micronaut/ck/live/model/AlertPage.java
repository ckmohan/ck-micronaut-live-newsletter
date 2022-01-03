package io.micronaut.ck.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public record AlertPage(@NonNull @NotBlank String title,
                        @NonNull @NotNull @Valid Alert alert) {
}
