package io.micronaut.ck.live.services;

import io.micronaut.ck.live.data.SubscriberDataRepository;
import io.micronaut.ck.live.model.SubscriptionStatus;
import jakarta.inject.Singleton;

@Singleton
public class UnSubscribeServiceImpl implements UnSubscribeService {
    private final SubscriberDataRepository subscriberDataRepository;

    public UnSubscribeServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public void unsubscribe(String email) {
        subscriberDataRepository.updateStatusByEmail(SubscriptionStatus.CANCELLED, email);

    }
}
