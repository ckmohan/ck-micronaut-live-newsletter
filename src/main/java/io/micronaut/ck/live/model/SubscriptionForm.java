package io.micronaut.ck.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
public record SubscriptionForm(@NonNull @NotBlank @Email String email) {
}
