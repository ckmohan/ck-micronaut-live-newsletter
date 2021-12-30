package io.micronaut.ck.live;

import io.micronaut.ck.live.data.SubscriberDataRepository;
import io.micronaut.ck.live.model.Email;
import io.micronaut.ck.live.services.EmailSender;
import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Property(name = "test.name", value = "SubscriptionConfirmationTest")
@MicronautTest
public class SubscriptionConfirmationTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    SubscriberDataRepository subscriberDataRepository;

    @Inject
    BeanContext beanContext;

    @Test
    void happyPathforSubscritionAndConfirmation() throws InterruptedException {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpResponse<Object> httpResponse = client.exchange(HttpRequest.POST("/api/v1/subscriber", Collections.singletonMap("email", "tcook@apple.com")));
        assertEquals(1, subscriberDataRepository.count());
        EmailSenderCollector emailSenderCollector = beanContext.getBean(EmailSenderCollector.class);
        TimeUnit.SECONDS.sleep(1);
        assertEquals(1, emailSenderCollector.emails.size());

    }

    @AfterEach
    void tearDown() {
        subscriberDataRepository.deleteAll();
    }

    @Requires(property = "test.name", value = "SubscriptionConfirmationTest")
    @Replaces(EmailSender.class)
    @Singleton
    static class EmailSenderCollector implements EmailSender {
        private final List<Email> emails = new ArrayList<>();

        @Override
        public void sendEmail(Email email) {
            emails.add(email);
        }
    }
}
