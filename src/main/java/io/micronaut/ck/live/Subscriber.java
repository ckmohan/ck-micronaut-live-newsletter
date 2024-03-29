package io.micronaut.ck.live;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public record Subscriber(
        @NotNull
        @NotBlank
        @Email
        String email, @Nullable String name) {
    public Subscriber(@NotNull @NotBlank
                      @Email String email) {
        this(email, null);
    }
}
