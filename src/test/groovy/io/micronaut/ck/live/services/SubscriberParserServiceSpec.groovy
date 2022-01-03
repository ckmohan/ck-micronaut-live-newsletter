package io.micronaut.ck.live.services

import io.micronaut.ck.live.Subscriber
import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import java.nio.file.Paths

@MicronautTest(startApplication = false)
class SubscriberParserServiceSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "ImportService can parse subscribers from CSV file"() {

        given:
        File f = Paths.get("src/test/resources/50-contacts.csv").toFile();

        expect:
        beanContext.containsBean(SubscriberParserService);
        f.exists()
        when:
        SubscriberParserService parserService = beanContext.getBean(SubscriberParserService)
        Optional<List<Subscriber>> subscribersOptional = parserService.parseSubscribers(f.newInputStream())

        then:
        noExceptionThrown()
        subscribersOptional.isPresent()
        subscribersOptional.get().size() == 50


    }
}
