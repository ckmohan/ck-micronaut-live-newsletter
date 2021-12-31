package io.micronaut.ck.live.conf;

import io.micronaut.context.BeanContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest(startApplication = false)
class EmailConfigurationTest {
    @Inject
    BeanContext beanContext;

    @Test
    void shouldNotContainBeanImplementation() {

        assertFalse(beanContext.containsBean(EmailConfiguration.class));
    }

}