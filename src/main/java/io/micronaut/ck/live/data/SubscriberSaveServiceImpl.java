package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.events.SubscriptionPendingEvent;
import io.micronaut.ck.live.services.IdGenerator;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class SubscriberSaveServiceImpl implements SubscriberSaveService {
    private final IdGenerator idGenerator;
    private final SubscriberDataRepository subscriberDataRepository;
    private final ApplicationEventPublisher<SubscriptionPendingEvent> eventPublisher;

    public SubscriberSaveServiceImpl(IdGenerator idGenerator,
                                     SubscriberDataRepository subscriberDataRepository,
                                     ApplicationEventPublisher<SubscriptionPendingEvent> eventPublisher) {
        this.idGenerator = idGenerator;
        this.subscriberDataRepository = subscriberDataRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @NonNull
    public Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber) {
        return idGenerator.generate().map(id -> {
            SubscriberEntity entity = new SubscriberEntity(id, subscriber.email(), subscriber.name());
            System.out.println(entity);
            subscriberDataRepository.save(entity);
            eventPublisher.publishEvent(new SubscriptionPendingEvent(subscriber.email()));
            return id;
        });
    }
}
