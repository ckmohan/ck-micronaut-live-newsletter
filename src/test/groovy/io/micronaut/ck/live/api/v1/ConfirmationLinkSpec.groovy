package io.micronaut.ck.live.api.v1


import io.micronaut.ck.live.services.ConfirmationEmailComposer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class ConfirmationLinkSpec extends Specification {
    @Inject
    ConfirmationEmailComposer composer

    void "Confirmation link is /subscriber/confirm"() {
        expect:
        composer.composeText("tcook@apple.com").contains("/subscriber/confirm")
    }
}
