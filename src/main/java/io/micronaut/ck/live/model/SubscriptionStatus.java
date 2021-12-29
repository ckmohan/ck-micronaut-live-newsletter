package io.micronaut.ck.live.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public enum SubscriptionStatus {
    PENDING,
    ACTIVE,
    CANCELLED
}
