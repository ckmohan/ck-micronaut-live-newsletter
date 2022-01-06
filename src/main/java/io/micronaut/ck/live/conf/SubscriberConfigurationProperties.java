package io.micronaut.ck.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;

@ConfigurationProperties("newsletter")
public record SubscriberConfigurationProperties(
        @NonNull Integer subscriberListPageSize) implements SubscriberConfiguration {
    private static final Integer DEFAULT_SUBSCRIBER_LIST_ZIE = 20;

    public SubscriberConfigurationProperties(@NonNull Integer subscriberListPageSize) {
        this.subscriberListPageSize = DEFAULT_SUBSCRIBER_LIST_ZIE;
    }
}
