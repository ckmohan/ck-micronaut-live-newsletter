package io.micronaut.ck.live.api.v1;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Introspected
public record EmailRequest(@NonNull @NotBlank String subject,
                           @Nullable String html,
                           @Nullable String text) {
    public EmailRequest(@NonNull @NotBlank String subject) {
        this(subject, null, null);
    }
}
