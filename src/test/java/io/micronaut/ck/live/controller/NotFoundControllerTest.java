package io.micronaut.ck.live.controller;

import io.micronaut.http.*;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class NotFoundControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;
    @Test
    void shouldContainHtlm() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<Object> request = HttpRequest.GET("/404").accept(MediaType.TEXT_HTML);
        HttpResponse<String> response = client.exchange(request, String.class);
        assertEquals(HttpStatus.OK,response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("<h1>Not Found</h1>"));
    }
}