package io.micronaut.ck.live.services;

import io.micronaut.context.BeanContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
class IdGeneratorTest {

    @Inject
    BeanContext beanContext;

    @Test
    void doesItContainBeanIfTypeIdGenerator() {
        boolean b = beanContext.containsBean(IdGenerator.class);
        assertTrue(b);

    }
}