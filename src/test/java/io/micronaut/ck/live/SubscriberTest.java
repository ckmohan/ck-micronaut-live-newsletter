package io.micronaut.ck.live;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
public class SubscriberTest {

    @Inject
    Validator validator;

    @Test
    void noConstrationValidationForValidSubscriber() {
        Subscriber subscriber = new Subscriber("test@gmail.com", null);
        boolean empty = validator.validate(subscriber).isEmpty();
        assertTrue(empty);
    }

    @Test
    void ConstrationValidationForValidSubscriberError() {
        Subscriber subscriber = new Subscriber(null, null);
        boolean empty = validator.validate(subscriber).isEmpty();
        assertFalse(empty);
    }

    @Test
    void ConstrationValidationForValidSubscriberMustBeValidEmailAddress() {
        Subscriber subscriber = new Subscriber("tesing", null);
        boolean empty = validator.validate(subscriber).isEmpty();
        assertFalse(empty);
    }
}