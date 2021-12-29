package io.micronaut.ck.live.data;

import io.micronaut.ck.live.model.SubscriptionStatus;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.micronaut.ck.live.model.SubscriptionStatus.PENDING;

@MappedEntity("subscriber")
public record SubscriberEntity(
        @Id @NonNull @NotBlank String id,
        @NotNull @NotBlank String email,
        @Nullable String name,
        SubscriptionStatus status)
        implements Entity<String> {
    public SubscriberEntity(
            @Id @NonNull @NotBlank String id,
            @NotNull @NotBlank String email,
            @Nullable String name) {
        this(id, email, name, PENDING);
    }
}
