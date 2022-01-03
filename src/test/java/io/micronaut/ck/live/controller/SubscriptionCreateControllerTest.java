package io.micronaut.ck.live.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class SubscriptionCreateControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void getSubscribeCreateRendersHtmlPage() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> accept = HttpRequest.GET("/subscriber/create").accept(MediaType.TEXT_HTML);
        HttpResponse<Object> httpResponse = client.exchange(accept);

        assertEquals(HttpStatus.OK, httpResponse.getStatus());
        assertTrue(httpResponse.getContentType().isPresent());
        assertEquals(MediaType.TEXT_HTML_TYPE, httpResponse.getContentType().get());

    }
}