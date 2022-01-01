package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

import static io.micronaut.ck.live.model.SubscriptionStatus.ACTIVE;

@Singleton
public class SubscriberServiceImpl implements SubscriberService {
    @Inject
    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberServiceImpl(SubscriberDataRepository subscriberDataRepository) {
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    public Integer getConfirmedSubscribersCount() {
        return subscriberDataRepository.countByStatus(ACTIVE);
    }

    @Override
    public List<Subscriber> findAll() {
        return subscriberDataRepository.findByStatus(ACTIVE);
    }
}
