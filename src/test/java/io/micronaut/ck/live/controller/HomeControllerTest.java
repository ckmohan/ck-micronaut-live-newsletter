package io.micronaut.ck.live.controller;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@MicronautTest
class HomeControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void redirectGetRequestToSubscriptionCreatePage() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpResponse<Object> httpResponse = client.exchange(HttpRequest.GET("/"));
        assertEquals(303, httpResponse.getStatus().getCode());
    }
}