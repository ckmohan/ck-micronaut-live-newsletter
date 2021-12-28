package io.micronaut.ck.live.services;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(startApplication = false)
class KsuidGeneratorTest {

    @Inject
    KsuidGenerator ksuidGenerator;

    @Test
    void idShouldBeDifferentWhenInvokedEverIInvokeIdGenerator() {
        Set<String> result = new HashSet<>();
        IntStream.rangeClosed(1, 10).forEach(i -> result.add(ksuidGenerator.generate().get()));
        assertEquals(10, result.size());

    }
}
