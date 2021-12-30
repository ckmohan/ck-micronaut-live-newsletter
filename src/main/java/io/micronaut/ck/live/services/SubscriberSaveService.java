package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface SubscriberSaveService {
    @NonNull
    Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber);
}
