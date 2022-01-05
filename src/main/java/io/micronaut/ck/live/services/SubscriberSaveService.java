package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface SubscriberSaveService {

    Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber);

    void saveActiveSubscribers(@NotNull @NonNull Collection<Subscriber> subscribers);

    boolean exists(@NonNull @NotNull @Email String email);


}
