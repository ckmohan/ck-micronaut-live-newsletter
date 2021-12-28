package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
class SubscriberSaveServiceImplTest {

    @Inject
    SubscriberSaveServiceImpl subscriberSaveService;
    @Inject
    SubscriberDataRepository subscriberDataRepository;

    @Test
    void shouldSaveSubscriber() {
        int before = (int) subscriberDataRepository.count();
        Subscriber subscriber = new Subscriber("tcook@apple.com", null);
        Optional<String> id = subscriberSaveService.save(subscriber);
        long count = subscriberDataRepository.count();
        assertEquals(count, before + 1);
        assertTrue(id.isPresent());
        subscriberDataRepository.deleteById(id.get());

    }
}