package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.events.SubscriptionPendingEvent;
import io.micronaut.ck.live.model.SubscriptionStatus;
import io.micronaut.ck.live.services.IdGenerator;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
            subscriberDataRepository.save(entity);
            eventPublisher.publishEvent(new SubscriptionPendingEvent(subscriber.email()));
            return id;
        });
    }

    @Override
    public void saveActiveSubscribers(@NonNull Collection<Subscriber> subscribers) {
        subscriberDataRepository.saveAll(cerateActiveSubscirberEntites(subscribers));
    }

    @Override
    public boolean exists(@NonNull @NotNull @Email String email) {
        return subscriberDataRepository.countByEmail(email) > 0;
    }

    private List<SubscriberEntity> cerateActiveSubscirberEntites(
            @NonNull Collection<Subscriber> subscribers) {
        List<SubscriberEntity> entities = new ArrayList<>();
        subscribers.forEach(subscriber ->
                createActiveSubscriberEntity(subscriber).ifPresent(entities::add));
        return entities;
    }

    private Optional<SubscriberEntity> createActiveSubscriberEntity(@NonNull Subscriber subscriber) {
        Optional<String> idOptional = idGenerator.generate();
        if (idOptional.isEmpty()) {
            return Optional.empty();
        }
        String id = idOptional.get();
        return Optional.of(
                new SubscriberEntity(id,
                        subscriber.email(),
                        subscriber.name(),
                        SubscriptionStatus.ACTIVE));
    }
}
