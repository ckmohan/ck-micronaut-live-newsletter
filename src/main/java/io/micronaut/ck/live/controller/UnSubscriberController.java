package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.services.ConfirmationCodeVerifier;
import io.micronaut.ck.live.services.UnSubscribeService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

@Controller("/unsubscribe")
class UnSubscriberController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final UnSubscribeService unSubscribeService;

    UnSubscriberController(ConfirmationCodeVerifier confirmationCodeVerifier, UnSubscribeService unSubscribeService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.unSubscribeService = unSubscribeService;
    }
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.TEXT_HTML)
    @View("unsubscribed")
    @Get
    HttpResponse<?> unsubscribe(@QueryValue @Nullable String token) {
        if (StringUtils.isEmpty(token)) {
            return notFound();
        }
        Optional<String> email = confirmationCodeVerifier.verify(token);
        if (!email.isPresent()) {
            return notFound();
        }
        unSubscribeService.unsubscribe(email.get());
        return HttpResponse.ok(Collections.emptyMap());
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException e) {
            return HttpResponse.serverError();
        }
    }
}
