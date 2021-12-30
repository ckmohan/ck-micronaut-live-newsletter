package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.model.Alert;
import io.micronaut.ck.live.model.SubscriptionForm;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Produces(MediaType.TEXT_HTML)
@Controller("/subscription")
class SubscriptionSaveController {
    private final SubscriberSaveService subscriberSaveService;

    SubscriptionSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/save")
    ModelAndView<Map<String, Object>> save(@Body @NonNull @NotNull @Valid SubscriptionForm form) {
        subscriberSaveService.save(new Subscriber(form.email(), null));
        return new ModelAndView<>("alert",
                Map.of("title", "Pending ~ Subscription", "alert", Alert
                        .builder()
                        .info("Please, check your email and confirm your subscription")
                        .build()));
    }
}
