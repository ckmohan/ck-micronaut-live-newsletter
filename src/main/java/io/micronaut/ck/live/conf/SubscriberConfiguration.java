package io.micronaut.ck.live.conf;

import io.micronaut.core.annotation.NonNull;

public sealed interface SubscriberConfiguration permits SubscriberConfigurationProperties {
    @NonNull
    Integer subscriberListPageSize();
}
