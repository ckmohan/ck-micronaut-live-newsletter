package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Property(name = "test.name", value = "SubscriberSaveServiceTest")
@MicronautTest(startApplication = false)
class SubscriberSaveServiceTest {
    @Inject
    SubscriberSaveService subscriberSaveService;

    @Test
    void throwExceptionWhenSubscriberIsNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            subscriberSaveService.save(null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "testing"})
    void subscriberInvalidValidEmailAddress(String email) {

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            Subscriber subscriber = new Subscriber(email, null);
            subscriberSaveService.save(subscriber);
        });
    }


    @Requires(property = "test.name", value = "SubscriberSaveServiceTest")
    @Replaces(SubscriberSaveService.class)
    @Singleton
    static class SubscriberSaveServiceStub implements SubscriberSaveService {
        @Override
        public Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber) {
            return Optional.empty();
        }
    }
}