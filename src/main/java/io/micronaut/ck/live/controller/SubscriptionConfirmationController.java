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
import io.micronaut.views.View;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

@Controller("/confirm")
class SubscriptionConfirmationController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final ConfirmationService confirmationService;

    SubscriptionConfirmationController(ConfirmationCodeVerifier confirmationCodeVerifier,
                                       ConfirmationService confirmationService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.confirmationService = confirmationService;
    }

    @Produces(MediaType.TEXT_HTML)
    @View("confirmed")
    @Get
    HttpResponse<?> confirm(@QueryValue @Nullable String token) {
        if (StringUtils.isEmpty(token)) {
            return notFound();
        }
        Optional<String> email = confirmationCodeVerifier.verify(token);
        if (!email.isPresent()) {
            return notFound();
        }
        confirmationService.confirm(email.get());
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
