package io.micronaut.ck.live.api.v1

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false)
class SubscriberCountControllerCorsSpec extends Specification{

    @Inject
    @Client("/")
    HttpClient httpClient;

    void "GET /api/v1/subscriber/count return CORS headers" () {
        given:
        BlockingHttpClient blockingHttpClient = httpClient.toBlocking()
        String path = "/api/v1//subscriber/count"


        def origin = 'groovycalamari.com'
        when:
        HttpResponse httpResponse = blockingHttpClient.exchange(
                HttpRequest.OPTIONS(path)
                        .header(HttpHeaders.ORIGIN, origin)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,"${HttpHeaders.CONTENT_TYPE},${HttpHeaders.ACCEPT}"))


        then:
        noExceptionThrown()

        when:
        Set<String> optionsHeaderNames = httpResponse.headers.names()
        then:
        httpResponse.status == HttpStatus.OK
        httpResponse.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN) == origin
        httpResponse.header(HttpHeaders.VARY) == HttpHeaders.ORIGIN
        optionsHeaderNames.contains(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)
        optionsHeaderNames.contains(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)

    }
}
