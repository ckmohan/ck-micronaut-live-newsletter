package io.micronaut.ck.live.views;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import java.util.List;

@Introspected
public record SubscriberListPage(@NonNull String title,
                                 @NonNull List<SubscriberRow> rows,
                                 @NonNull Pagination pagination) {
}

