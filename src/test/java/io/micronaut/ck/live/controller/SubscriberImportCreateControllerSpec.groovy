package io.micronaut.ck.live.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false)
class SubscriberImportCreateControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /subscriber/import rednder a HTML page with a form"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = HttpRequest.GET("/subscriber/import").accept(MediaType.TEXT_HTML);
        when:
        String html = client.retrieve(request);
        then:
        noExceptionThrown()
        html.concat("type=\"file\" id=\"file\" name=\"file\"")
    }
}
