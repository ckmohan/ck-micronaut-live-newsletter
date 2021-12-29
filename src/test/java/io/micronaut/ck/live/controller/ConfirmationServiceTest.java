package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.data.ConfirmationService;
import io.micronaut.context.BeanContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@MicronautTest(startApplication = false)
class ConfirmationServiceTest {
    @Inject
    BeanContext beanContext;

    @Test
    void shouldFindBeanOfTypeConfirmationService() {
        assertTrue(beanContext.containsBean(ConfirmationService.class));
    }
}