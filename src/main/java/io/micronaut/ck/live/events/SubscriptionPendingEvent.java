package io.micronaut.ck.live.events;

import io.micronaut.core.annotation.NonNull;

public record SubscriptionPendingEvent(@NonNull String email) {
}
