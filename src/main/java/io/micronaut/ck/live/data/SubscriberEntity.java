package io.micronaut.ck.live.data;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedEntity("subscriber")
public record SubscriberEntity(
        @Id @NonNull @NotBlank String id,
        @NotNull @NotBlank String email,
        @Nullable String name,
        boolean confirmed,
        boolean unsubscribed)
        implements Entity<String> {
    public SubscriberEntity(
            @Id @NonNull @NotBlank String id,
            @NotNull @NotBlank String email,
            @Nullable String name) {
        this(id, email, name, false, false);
    }
}
