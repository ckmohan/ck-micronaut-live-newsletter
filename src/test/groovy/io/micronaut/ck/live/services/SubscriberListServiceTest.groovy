package io.micronaut.ck.live.services

import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class SubscriberListServiceTest extends Specification {

    @Inject
    BeanContext beanContext;
    void "bean of tye SubscriberListService exits" (){
        expect:
        beanContext.containsBean(SubscriberListService)
    }
}
