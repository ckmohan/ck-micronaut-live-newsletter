package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public sealed interface SubscriberParserService permits DefaultSubscriberParserService {
    Optional<List<Subscriber>> parseSubscribers(@NonNull @NotNull InputStream inputStream);
}
