package io.micronaut.ck.live.data;

import io.micronaut.ck.live.services.SubscriberCountService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class SubscriberCountServiceImpl implements SubscriberCountService {
    @Inject
    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberCountServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public Integer getConfirmedSubscribersCount() {
        return subscriberDataRepository.countByConfirmedAndUnsubscribed(true,false);
    }
}
