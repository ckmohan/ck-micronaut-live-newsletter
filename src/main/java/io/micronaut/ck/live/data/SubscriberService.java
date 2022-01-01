package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.core.annotation.NonNull;

import java.util.List;

public interface SubscriberService  {
    @NonNull
    Integer getConfirmedSubscribersCount();
    @NonNull
    List<Subscriber> findAll();
}
