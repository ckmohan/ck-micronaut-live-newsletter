package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.services.ConfirmationCodeGenerator;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class UnSubscriberControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    ConfirmationCodeGenerator confirmationCodeGenerator;

    @Test
    void tokenNotSuppliedOnUnsubscribeShouldRedirectToNotFound() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = createRequest(null);
        HttpResponse<String> response = client.exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("<h1>Not Found</h1>"));

    }

    @Test
    void inValidTokenSuppliedOnUnsubscribeShouldRedirectToUnsubscribedPage() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = createRequest("tcook");
        HttpResponse<String> response = client.exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("<h1>Not Found</h1>"));

    }

    @Test
    void tokenSuppliedOnUnsubscribeShouldRedirectToUnsubscribedPage() {
        BlockingHttpClient client = httpClient.toBlocking();
        Optional<String> generate = confirmationCodeGenerator.generate("tcook@apple.com");
        HttpRequest<?> request = createRequest(generate.get());
        HttpResponse<String> response = client.exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("<h1>You are no longer subscribed</h1>"));

    }

    HttpRequest<?> createRequest(@Nullable String token) {
        UriBuilder builder = UriBuilder.of("/unsubscribe");
        if (token != null) {
            builder.queryParam("token", token);
        }
        return HttpRequest.GET(builder.build()).accept(MediaType.TEXT_HTML);
    }
}