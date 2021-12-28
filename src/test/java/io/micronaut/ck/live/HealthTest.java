package io.micronaut.ck.live;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class HealthTest {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void healthEnpointExposed() {
        BlockingHttpClient client = httpClient.toBlocking();
        client.exchange(HttpRequest.GET("/health"));

    }
}
