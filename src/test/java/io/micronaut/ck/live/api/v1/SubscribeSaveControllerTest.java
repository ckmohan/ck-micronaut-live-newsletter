package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.CREATED;
import static org.junit.jupiter.api.Assertions.*;

@Property(name = "test.name", value = "SubscribeSaveControllerTest")
@MicronautTest
class SubscribeSaveControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    SubscriberSaveService subscriberSaveService;

    @Test
    void missingSubscriberReturns400() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpClientResponseException responseException = assertThrows(HttpClientResponseException.class, () -> {
            client.exchange(HttpRequest.POST("/api/v1/subscriber", "{}"));
        });
        assertEquals(BAD_REQUEST, responseException.getStatus());
        Assertions.assertTrue(responseException.getResponse().getContentType().isPresent());
        assertEquals("application/problem+json", responseException.getResponse().getContentType().get().toString());
    }

    @Test
    void missingSubscriberWithInvalidEmailReturns400() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpClientResponseException responseException = assertThrows(HttpClientResponseException.class, () -> {
            client.exchange(HttpRequest.POST("/api/v1/subscriber", Collections.singletonMap("email", "invalie")));
        });
        assertEquals(BAD_REQUEST, responseException.getStatus());
        Assertions.assertTrue(responseException.getResponse().getContentType().isPresent());
        assertEquals("application/problem+json", responseException.getResponse().getContentType().get().toString());
    }

    @Test
    void postSubscriberAndDelegateToSubscriberSaveService() {

        BlockingHttpClient client = httpClient.toBlocking();
        HttpResponse<Object> httpResponse = client.exchange(HttpRequest.POST("/api/v1/subscriber", Collections.singletonMap("email", "tcook@apple.com")));
        assertEquals(CREATED, httpResponse.getStatus());
        assertTrue(subscriberSaveService instanceof SubscriberSaveServiceStub);

    }

    @Requires(property = "test.name", value = "SubscribeSaveControllerTest")
    @Replaces(SubscriberSaveService.class)
    @Singleton
    static class SubscriberSaveServiceStub implements SubscriberSaveService {
        int invocations;

        @Override
        @NonNull
        public Optional<String> save(@NotNull @NonNull @Valid Subscriber subscriber) {
            invocations++;
            return Optional.empty();
        }
    }

}