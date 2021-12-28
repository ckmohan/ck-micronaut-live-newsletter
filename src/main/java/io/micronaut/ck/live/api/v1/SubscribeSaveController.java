package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/api/v1")
class SubscribeSaveController {

    private final SubscriberSaveService subscriberSaveService;

    SubscribeSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Post("/subscriber")
    @Status(HttpStatus.CREATED)
    void saveSubscriber(@NotNull @NonNull @Valid Subscriber subscriber) {
        subscriberSaveService.save(subscriber);
    }
}
