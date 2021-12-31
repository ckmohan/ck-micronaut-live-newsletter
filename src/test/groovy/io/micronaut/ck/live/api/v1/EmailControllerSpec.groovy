package io.micronaut.ck.live.api.v1

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class EmailControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient;

    void "POST /api/v1 email return 202"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking();
        EmailRequest body = new EmailRequest("[Groovy Calamari] 179 - Groovy Calamari Returns");

        when:
        HttpResponse<?> httpResponse = client.exchange(HttpRequest.POST("/api/v1/email", body))

        then:
        noExceptionThrown()
        202 == httpResponse.status().code
    }
}
