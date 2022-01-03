package io.micronaut.ck.live.api.v1

import io.micronaut.ck.live.Subscriber
import io.micronaut.ck.live.data.SubscriberService
import io.micronaut.ck.live.model.Email
import io.micronaut.ck.live.services.EmailSender
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Property(name = "spec.name", value = "EmailControllerSpec")
@Property(name = "email.from", value = "tcook@apple.com")
@MicronautTest
class EmailSendControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    BeanContext beanContext;

    void "POST /api/v1/email return 202"() {
        given:
        String subject = "[Groovy Calamari] 179 - Groovy Calamari Returns"
        BlockingHttpClient client = httpClient.toBlocking();
        EmailRequest body = new EmailRequest(subject);

        when:
        HttpResponse<?> httpResponse = client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        noExceptionThrown()
        202 == httpResponse.status().code

        and:
        new PollingConditions().eventually {
            {
                assert beanContext.getBean(EmailSenderReplacement).emails.size() == 2
                assert beanContext.getBean(EmailSenderReplacement).emails.any { email ->
                    email.subject() == subject && email.to() == "cook@apple.com" && email.from() == "tcook@apple.com"
                }
                assert beanContext.getBean(EmailSenderReplacement).emails.any { email ->
                    email.subject() == subject && email.to() == "look@apple.com" && email.from() == "tcook@apple.com"
                }
            }
        }
    }

    @Requires(property = "spec.name", value = "EmailControllerSpec")
    @Singleton
    @Replaces(SubscriberService.class)
    static class SubscriberServiceReplacement implements SubscriberService {

        @Override
        Integer getConfirmedSubscribersCount() {
            2
        }
        @Override
        @NonNull
        List<Subscriber> findAll() {
            [new Subscriber("cook@apple.com"), new Subscriber("look@apple.com")]
        }
    }

    @Requires(property = "spec.name", value = "EmailControllerSpec")
    @Singleton
    @Replaces(EmailSender.class)
    static class EmailSenderReplacement implements EmailSender {
        List<Email> emails = []

        @Override
        void sendEmail(@NonNull @NotBlank @Valid Email email) {
            emails.add(email)
        }
    }

    void "POST /api/v1/email with invalid payload return 400"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking();
        EmailRequest body = new EmailRequest("");

        when:
        client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        HttpClientResponseException e = thrown()
        400 == e.status.code
    }
}

