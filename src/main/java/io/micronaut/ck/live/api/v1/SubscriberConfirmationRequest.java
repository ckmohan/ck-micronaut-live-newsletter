package io.micronaut.ck.live.api.v1;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public record SubscriberConfirmationRequest(
        @NonNull
        @NotBlank
        String token) {
}
