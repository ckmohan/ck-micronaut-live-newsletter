package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.services.IdGenerator;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class SubscriberSaveServiceImpl implements SubscriberSaveService {
    private final IdGenerator idGenerator;
    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberSaveServiceImpl(IdGenerator idGenerator, SubscriberDataRepository subscriberDataRepository) {
        this.idGenerator = idGenerator;
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    @NonNull
    public Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber) {
        return idGenerator.generate().map(id -> {
            SubscriberEntity entity = new SubscriberEntity(id, subscriber.email(), subscriber.name());
            subscriberDataRepository.save(entity);
            return id;
        });
    }
}
