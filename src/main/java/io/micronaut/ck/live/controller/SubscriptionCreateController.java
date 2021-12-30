package io.micronaut.ck.live.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;

import java.util.Collections;
import java.util.Map;

@Controller("/subscription")
class SubscriptionCreateController {
    @Produces(MediaType.TEXT_HTML)
    @View("subscriptioncreate")
    @Get("/create")
    Map<String, Object> create() {
        return Collections.emptyMap();
    }
}
