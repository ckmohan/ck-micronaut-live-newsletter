package io.micronaut.ck.live.controller

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.Micronaut
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.shaded.org.bouncycastle.crypto.engines.EthereumIESEngine
import spock.lang.Ignore
import spock.lang.Specification


@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest
@Ignore
class SubscriberListControllerSpec extends Specification { //implements PostgresTestPropertyProvider {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "GET /subscriber/list displays the subscribers HTML page"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/subscriber/list')
                .accept(MediaType.TEXT_HTML)
        String html = client.retrieve(request)

        then:
        noExceptionThrown()
        html
        html.contains('<h1>Subscribers</h1>')

    }
}
