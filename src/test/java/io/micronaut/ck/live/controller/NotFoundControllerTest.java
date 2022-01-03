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
class NotFoundControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void shouldContainHtml() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<Object> request = HttpRequest.GET("/404").accept(MediaType.TEXT_HTML);
        HttpResponse<String> response = client.exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("""
                            <div class="alert alert-danger" role="alert">
                                    <span>Not Found</span>
                                </div>"""));
    }
}