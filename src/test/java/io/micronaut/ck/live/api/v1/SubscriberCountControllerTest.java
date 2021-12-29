package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.data.SubscriberDataRepository;
import io.micronaut.ck.live.data.SubscriberEntity;
import io.micronaut.ck.live.services.IdGenerator;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(transactional = false)
class SubscriberCountControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    SubscriberDataRepository subscriberDataRepository;

    @Inject
    IdGenerator idGenerator;

    @AfterEach
    void tearDown() {
        subscriberDataRepository.deleteAll();
    }

    @Test
    void shouldReturnCountZero() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> httpRequest = HttpRequest.GET("/api/v1/subscriber/count").accept(MediaType.TEXT_PLAIN);
        Integer result = client.retrieve(httpRequest, Integer.class);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnCountAsOne() {
        BlockingHttpClient client = httpClient.toBlocking();
        SubscriberEntity entity = new SubscriberEntity(idGenerator.generate().get(),
                "tcook@apple.com", "Tim Cook", true,false);
        subscriberDataRepository.save(entity);
        HttpRequest<?> httpRequest = HttpRequest.GET("/api/v1/subscriber/count").accept(MediaType.TEXT_PLAIN);
        Integer result = client.retrieve(httpRequest, Integer.class);
        assertEquals(1, result);
    }

    @Test
    void shouldReturnCountAsOneWithUnsubscribed() {
        BlockingHttpClient client = httpClient.toBlocking();
        SubscriberEntity entity = new SubscriberEntity(idGenerator.generate().get(),
                "tcook@apple.com", "Tim Cook", true,false);
        SubscriberEntity entity1 = new SubscriberEntity(idGenerator.generate().get(),
                "tcook@apple.com", "Tim Cook", true,true);
        subscriberDataRepository.save(entity);
        subscriberDataRepository.save(entity1);
        HttpRequest<?> httpRequest = HttpRequest.GET("/api/v1/subscriber/count").accept(MediaType.TEXT_PLAIN);
        Integer result = client.retrieve(httpRequest, Integer.class);
        assertEquals(1, result);
    }

}