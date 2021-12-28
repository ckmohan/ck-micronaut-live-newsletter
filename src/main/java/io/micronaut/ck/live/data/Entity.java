package io.micronaut.ck.live.data;

public sealed interface Entity<T> permits SubscriberEntity {
    T id();
}
