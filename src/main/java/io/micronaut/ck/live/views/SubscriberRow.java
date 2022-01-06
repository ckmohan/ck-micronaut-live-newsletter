package io.micronaut.ck.live.views;

import io.micronaut.ck.live.model.SubscriptionStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

@Introspected
public record SubscriberRow(@NonNull String id,
                            @NonNull String email,
                            @NonNull SubscriptionStatus status,
                            @Nullable String name) {
}
